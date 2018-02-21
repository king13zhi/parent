package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.business.domain.BidRequest;
import com._163.king13zhi.p2p.business.domain.PaymentSchedule;
import com._163.king13zhi.p2p.business.domain.PaymentScheduleDetail;
import com._163.king13zhi.p2p.business.domain.SystemAccount;
import com._163.king13zhi.p2p.business.mapper.PaymentScheduleMapper;
import com._163.king13zhi.p2p.business.query.PaymentScheduleQueryObject;
import com._163.king13zhi.p2p.business.service.*;
import com._163.king13zhi.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/28.
 */
@Service
@Transactional
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
	@Autowired
	private PaymentScheduleMapper paymentScheduleMapper;
	@Autowired
	private IPaymentScheduleDetailService paymentScheduleDetailService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService accountFlowService;
	@Autowired
	private ISystemAccountService systemAccountService;
	@Autowired
	private ISystemAccountFlowService systemAccountFlowService;
	@Autowired
	private IBidService bidService;
	@Autowired
	private IBidRequestService bidRequestService;

	@Override
	public int save(PaymentSchedule paymentSchedule) {
		return paymentScheduleMapper.insert(paymentSchedule);
	}

	@Override
	public int update(PaymentSchedule paymentSchedule) {
		return paymentScheduleMapper.updateByPrimaryKey(paymentSchedule);
	}

	@Override
	public PaymentSchedule get(Long id) {
		return paymentScheduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo queryPage(PaymentScheduleQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List list = paymentScheduleMapper.pagePage(qo);
		return new PageInfo(list);
	}

	@Override
	public void returnMoney(Long id) {
		PaymentSchedule ps = this.get(id);
		//1.获取还款对象,状态为正常偿还状态
		if ( ps != null && ps.getState() == BidConst.PAYMENT_STATE_NORMAL ) {
			//当前还款的id不是借款人的id(也就是说不能是借款人)
			if ( UserContext.getCurrent().getId().equals(ps.getBorrowUser().getId()) ) {
				Account account = accountService.getCurrent();
				//2.还款对象和还款明细对象属性设置
				//	还款对象设置状态-->已还
				ps.setState(BidConst.PAYMENT_STATE_DONE);
				//	还款对象设置状态设置还款日期
				ps.setPayDate(new Date());
				//	还款明细对象状态设置还款日期(生成一个方法统一进行状态更改)
				paymentScheduleMapper.updateByPrimaryKey(ps);
				paymentScheduleDetailService.updatePayDate(ps.getId(), ps.getPayDate());
				//3.还款人账户信息
				//	可用金额减少,待还金额减少,授信额度增加(到还款的本金)
				account.setUsableAmount(account.getUsableAmount().subtract(ps.getTotalAmount()));
				account.setUnReturnAmount((account.getUnReturnAmount()).subtract(ps.getTotalAmount()));
				account.setRemainBorrowLimit(account.getRemainBorrowLimit().add(ps.getPrincipal()));
				accountService.update(account);
				//	生成还款成功的流水
				accountFlowService.createReturnMoneyFlow(account, ps.getTotalAmount());

				//4.投资人账户变化
				Long bidUserId;
				Account bidUserAccount;
				Map<Long, Account> accountMap = new HashMap<>();
				BigDecimal interestManagerCharge;
				SystemAccount systemAccount = systemAccountService.selectCurrent();
				for (PaymentScheduleDetail psd : ps.getDetails()) {
					bidUserId = psd.getInvestorId();
					bidUserAccount = accountMap.get(bidUserId);
					if ( bidUserAccount == null ) {
						bidUserAccount = accountService.get(bidUserId);
						accountMap.put(bidUserId, bidUserAccount);
					}
					//	可用金额增加,代收本金和代收利息减少
					bidUserAccount.setUsableAmount(bidUserAccount.getUsableAmount().add(psd.getTotalAmount()));
					bidUserAccount.setUnReceivePrincipal(bidUserAccount.getUnReceivePrincipal().subtract(psd.getPrincipal()));
					bidUserAccount.setUnReceiveInterest(bidUserAccount.getUnReceiveInterest().subtract(psd.getInterest()));
				//	生成回款成功的流水
					accountFlowService.createGainPrincipalAndInteresFlow(bidUserAccount,psd.getTotalAmount());

					//	投资人支付利息管理费(利息的10%),投资人可用金额减少.
					interestManagerCharge = CalculatetUtil.calInterestManagerCharge(psd.getInterest());
					//	生成支付利息管理费的流水
					bidUserAccount.setUsableAmount(bidUserAccount.getUsableAmount().subtract(interestManagerCharge));
					//	平台账户收取利息管理费
					accountFlowService.createPayInterestManagerChargeFlow(bidUserAccount,interestManagerCharge);
					//	生成平台账户收取利息管理费的流水
					systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestManagerCharge));
					systemAccountFlowService.createGainInterestManagerChargeFlow(systemAccount,interestManagerCharge);
				}
				//统一账户信息修改
				for (Account accountTemp : accountMap.values()) {
					accountService.update(accountTemp);
				}
				systemAccountService.update(systemAccount);

				//5.判断这次借款所有的还款都已经还清
				List<PaymentSchedule> psSchedules = paymentScheduleMapper.queryByBidRequestId(ps.getBidRequestId());
				//	通过借款的id获取到该借款的所有还款,判断所有的还款对象状态是否变成已还清
				for (PaymentSchedule psSchedule : psSchedules) {
					if ( psSchedule.getState() != BidConst.PAYMENT_STATE_DONE ) {
						return;
					}
				}
				//6.借款还清,借款对象和投标对象属性设置
				BidRequest bidRequest = bidRequestService.get(ps.getBidRequestId());
				bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
				bidRequestService.update(bidRequest);
				//	借款对象和投标对象的状态修改成  已还清
				bidService.updateState(ps.getBidRequestId(),BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
			}
		}
	}
}

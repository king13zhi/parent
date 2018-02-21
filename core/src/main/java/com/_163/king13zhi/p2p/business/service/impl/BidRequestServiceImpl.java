package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.BitStatesUtils;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.business.domain.*;
import com._163.king13zhi.p2p.business.mapper.BidRequestMapper;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;
import com._163.king13zhi.p2p.business.service.*;
import com._163.king13zhi.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by kingdan on 2018/1/25.
 */
@Service
@Transactional
public class BidRequestServiceImpl implements IBidRequestService {
	@Autowired
	private BidRequestMapper bidRequestMapper;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IBidRequestAuditHistoryService bidRequestAuditHistoryService;
	@Autowired
	private IBidService bidService;
	@Autowired
	private IAccountFlowService accountFlowService;
	@Autowired
	private ISystemAccountService systemAccountService;
	@Autowired
	private ISystemAccountFlowService systemAccountFlowService;
	@Autowired
	private IPaymentScheduleService paymentScheduleService;
	@Autowired
	private IPaymentScheduleDetailService paymentScheduleDetailService;

	@Override
	public int save(BidRequest bidRequest) {
		return bidRequestMapper.insert(bidRequest);
	}

	@Override
	public int update(BidRequest bidRequest) {
		int count = bidRequestMapper.updateByPrimaryKey(bidRequest);
		if ( count == 0 ) {
			throw new RuntimeException("乐观锁异常,bidRequest" + bidRequest.getId());
		}
		return count;
	}

	@Override
	public BidRequest get(Long id) {
		return bidRequestMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean canApplyBorrow(Userinfo userinfo) {
		//判断用户是否进行完四大认证
		if ( (userinfo.getHasBindEmail()) || (userinfo.getIsBasicInfo()) ||
				(userinfo.getIsRealAuth()) || (userinfo.getScore() >= BidConst.CREDIT_BORROW_SCORE) ) {
			return true;
		}
		return false;
	}

	@Override
	public void apply(BidRequest bidRequest) {
		Userinfo userinfo = userinfoService.getCurrent();
		Account account = accountService.getCurrent();
		if ( this.canApplyBorrow(userinfo) && //1.判断当前用户是否具有借款的资格
				!userinfo.getHasBidRequestProcess() && //2.该用户没有借款的流程
				bidRequest.getBidRequestAmount().compareTo(BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0 && //用户借款金额>=系统最小借款金额
				bidRequest.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0 && //用户借款金额 <= 用户剩余授信额度
				bidRequest.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0 && //借款利率 >= 系统最小借款年华利率
				bidRequest.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0 && //借款利率 <= 系统最大借款年华利率
				bidRequest.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0 //用户设定的最小投标 >= 系统最小投标
				) {
			//创建BIdrequest对象,设置相关的属性
			BidRequest br = new BidRequest();
			br.setApplyTime(new Date()); //借款申请时间
			br.setBidRequestAmount(bidRequest.getBidRequestAmount()); //用户借款金额
			br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING); //借款对象的状态(待发布)
			br.setBidRequestType(BidConst.BIDREQUEST_TYPE_NORMAL);//借款类型(信用标)
			br.setCreateUser(UserContext.getCurrent()); //借款人
			br.setCurrentRate(bidRequest.getCurrentRate());//借款利率
			br.setDescription(bidRequest.getDescription()); //借款描述
			br.setDisableDays(bidRequest.getDisableDays()); //招标天数
			br.setMinBidAmount(bidRequest.getMinBidAmount()); //投标的最小金额
			br.setMonthes2Return(bidRequest.getMonthes2Return()); //还款的方式(按月分期(等额本息))
			br.setReturnType(BidConst.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL);
			br.setTitle(bidRequest.getTitle());
			br.setTotalRewardAmount(CalculatetUtil.calTotalInterest
					(br.getReturnType(), br.getBidRequestAmount(), br.getCurrentRate(), br.getMonthes2Return()));
			this.save(br);
			//找到当前登录用户的userinfo,给用户添加正在借款的状态码
			userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
			userinfoService.update(userinfo);
		}
	}

	@Override
	public PageInfo queryPage(BidRequestQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List list = bidRequestMapper.queryPage(qo);
		return new PageInfo(list);
	}

	@Override
	public void publishAudit(Long id, int state, String remark) {
		//1.条件判断
		//根据id查询bidRequest对象,判断借款对象是否处于待审核的状态
		BidRequest bidRequest = this.get(id);
		if ( bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING ) {
			//创建审核历史对象(设置审核人,审核时间,审核备注)
			BidRequestAuditHistory bidRequestAuditHistory = new BidRequestAuditHistory();
			bidRequestAuditHistory.setApplier(bidRequest.getCreateUser());
			bidRequestAuditHistory.setApplyTime(bidRequest.getApplyTime());
			bidRequestAuditHistory.setAuditor(UserContext.getCurrent());
			bidRequestAuditHistory.setAuditTime(new Date());
			bidRequestAuditHistory.setRemark(remark);
			bidRequestAuditHistory.setBidRequestId(bidRequest.getId());
			bidRequestAuditHistory.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);
			if ( state == BidRequestAuditHistory.STATE_PASS ) {
				//审核通过  招标截止时间,标的状态(招标),标的风险意见
				bidRequestAuditHistory.setState(BidRequestAuditHistory.STATE_PASS);
				bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
				bidRequest.setPublishTime(new Date());
				bidRequest.setNote(remark);
				bidRequest.setDisableDate(DateUtils.addDays(bidRequest.getPublishTime(), bidRequest.getDisableDays()));
			} else {
				//审核拒绝
				bidRequestAuditHistory.setState(BidRequestAuditHistory.STATE_REJECT);
				//标的状态表更拒绝
				bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
				//申请人移除借款状态码
				Userinfo createUserUserinfo = userinfoService.get(bidRequest.getCreateUser().getId());
				createUserUserinfo.removeStae(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
				userinfoService.update(createUserUserinfo);
			}
			bidRequestAuditHistoryService.save(bidRequestAuditHistory);
			this.update(bidRequest);
		}
	}

	@Override
	public List<BidRequest> queryIndexList(BidRequestQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List list = bidRequestMapper.queryPage(qo);
		return list;
	}

	@Override
	public void bid(Long bidRequestId, BigDecimal amount) {
		BidRequest bidRequest = this.get(bidRequestId);
		if ( bidRequest != null &&
				bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_BIDDING
				) {
			if ( !UserContext.getCurrent().getId().equals(bidRequest.getCreateUser().getId()) ) {
				//判断当前登录的用户是否为借款人
				Account account = accountService.getCurrent();
				//根据id获取bidrequest对象,判断是否处于招标中
				if ( amount.compareTo(bidRequest.getMinBidAmount()) >= 0 && //招标金额>=借款设置最小投标金额
						amount.compareTo(account.getUsableAmount().min(bidRequest.getRemainAmount())) <= 0 //将招标金额<= min(账户金额,该标剩余的金额)
						) {
					//标属性中的投标次数 目前所投钱增加
					bidRequest.setBidCount(bidRequest.getBidCount() + 1);
					bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));

					//投标属性
					Bid bid = new Bid();
					bid.setActualRate(bidRequest.getCurrentRate()); //借款的年华利率
					bid.setAvailableAmount(amount); //投标
					bid.setBidRequestId(bidRequest.getId()); //关联那个借款
					bid.setBidRequestTitle(bidRequest.getTitle()); //借款标题
					bid.setBidTime(new Date());
					bid.setBidUser(UserContext.getCurrent());
					bid.setBidRequestState(bidRequest.getBidRequestState());
					bidService.save(bid);

					//投标人账户
					account.setUsableAmount(account.getUsableAmount().subtract(amount));
					account.setFreezedAmount(account.getFreezedAmount().add(amount));
					accountService.update(account);

					accountFlowService.createBidFlow(account, amount);

					//当前已投的金额==借款的金额
					if ( bidRequest.getCurrentSum().compareTo(bidRequest.getBidRequestAmount()) == 0 ) {
						//如果投满,借款对象进入满标一审状态
						bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
						//投标对象进入满标一审状态
						bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
					}
					this.update(bidRequest);
				}

			}
		}
	}

	@Override
	public void bidrequestAudit1(Long id, int state, String remark) {
		//2.1判断借款的状态是否在满标一审的状态
		BidRequest bidRequest = this.get(id);
		if ( bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1 ) {
			//2.2创建审核对象BidRequestAuditHistory对象,设置相关属性
			createBidRequestAuditHistory(BidRequestAuditHistory.AUDIT1, state, remark, bidRequest); //抽离成一个方法
			//2.3审核通过
			if ( state == BidRequestAuditHistory.STATE_PASS ) {
				//2.3.1修改借款对象的状态---->满标二审状态
				bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
				//2.3.2修改投标对象的状态---->满标二审状态
				bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
			} else {
				bidRejectFlow(bidRequest);
			}
			this.update(bidRequest);
		}
	}

	//一审 二审 审核拒绝业务逻辑
	private void bidRejectFlow(BidRequest bidRequest) {
		//2.4.1修改借款对象的状态---->满标拒绝
		bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
		//2.4.2修改投标对象的状态---->满标拒绝
		bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_REJECTED);
		//2.4.3遍历投标对象,把投资的钱归还.
		List<Bid> bids = bidRequest.getBids();
		Map<Long, Account> accountMap = new HashMap<>();
		Long bidUserId;
		Account bidUserAccount;
		for (Bid bid : bids) {
			bidUserId = bid.getBidUser().getId();
			bidUserAccount = accountMap.get(bidUserId);
			//查询出每个投资对象.
			if ( bidUserAccount == null ) {
				bidUserAccount = accountService.get(bidUserId);
				accountMap.put(bidUserId, bidUserAccount);
			}
			//可用金额增加,冻结金额减少.
			bidUserAccount.setFreezedAmount(bidUserAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
			bidUserAccount.setUsableAmount(bidUserAccount.getUsableAmount().add(bid.getAvailableAmount()));
			//生成投标失败的流水
			accountFlowService.createBidFaileFlow(bidUserAccount, bid.getAvailableAmount());
		}
		//对账户进行统一的更新修改
		for (Account account : accountMap.values()) {
			accountService.update(account);
		}
		//更新投标账号
		//2.4.4 借款人userinfo对象去掉正在借款的状态码,更新借款人userinfo对象
		//找到借款人的userinfo,移除借款的状态码
		Userinfo createUserUserinfo = userinfoService.get(bidRequest.getCreateUser().getId());
		createUserUserinfo.removeStae(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
		userinfoService.update(createUserUserinfo);
	}

	//历史对象公共方法
	private void createBidRequestAuditHistory(int auditType, int state, String remark, BidRequest bidRequest) {
		BidRequestAuditHistory bah = new BidRequestAuditHistory();
		bah.setApplier(bidRequest.getCreateUser());
		bah.setApplyTime(new Date());
		bah.setAuditor(UserContext.getCurrent());
		bah.setAuditTime(new Date());
		bah.setBidRequestId(bidRequest.getId());
		bah.setRemark(remark);
		bah.setAuditType(auditType);
		if ( state == BidRequestAuditHistory.STATE_PASS ) {
			bah.setState(BidRequestAuditHistory.STATE_PASS);
		} else {
			bah.setState(BidRequestAuditHistory.STATE_REJECT);
		}
		bidRequestAuditHistoryService.save(bah);
	}

	@Override
	public void bidrequestAudit2(Long id, int state, String remark) {
		//1.获取到BiRequest中的id,根据Id查询对于的借款对象信息.
		BidRequest bidRequest = this.get(id);
		//2.判断bidRequest对象是否为null&&判断bidRequest是否在满标二审的状态{
		if ( bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2 ) {
			//创建一个审核历史对象 保存审核历史对象
			createBidRequestAuditHistory(BidRequestAuditHistory.AUDIT2, state, remark, bidRequest);
			//设置审核相关的属性
			if ( state == BidRequestAuditHistory.STATE_PASS ) {
				//设置借款对象的的状态--->还款中
				bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
				//设置投标对象的状态---->还款中
				bidService.updateState(bidRequest.getId(), BidConst.BIDREQUEST_STATE_PAYING_BACK);

				//对于借款对象的操作
				Account createUserAccount = accountService.get(bidRequest.getCreateUser().getId());
				//借款人的可用金额添加
				createUserAccount.setUsableAmount(createUserAccount.getUsableAmount().add(bidRequest.getBidRequestAmount()));
				//借款人的剩余授信额度减少
				createUserAccount.setRemainBorrowLimit(createUserAccount.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
				//用户的待换金额增加(本金+利息)
				createUserAccount.setUnReturnAmount(createUserAccount.getUnReturnAmount().add(bidRequest.getBidRequestAmount().add(bidRequest.getTotalRewardAmount())));
				//增加借款流水对象(借款的总资金)
				accountFlowService.createBidRequestSuccessFlow(createUserAccount, bidRequest.getBidRequestAmount());
				//支付平台的管理费(初始化平台的系统账户和系统账户流水)
				//系统账户的可用金额增加  accountManagermentCharge 支付平台管理费
				BigDecimal accountManagermentCharge = CalculatetUtil.calAccountManagementCharge(bidRequest.getBidRequestAmount());
				createUserAccount.setUsableAmount(createUserAccount.getUsableAmount().subtract(accountManagermentCharge));
				//增加系统用户收取平台管理费的流水
				accountFlowService.createSystemAccountSuccessFlow(createUserAccount, accountManagermentCharge);
				//移除借款用户userinfo对象<正在借款>的状态码
				Userinfo createUserinfo = userinfoService.get(bidRequest.getCreateUser().getId());
				createUserinfo.removeStae(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
				//更新userinfo对象
				userinfoService.update(createUserinfo);
				//更新借款人对象
				accountService.update(createUserAccount);
				//系统账户手续借款手续费
				SystemAccount systemAccount = systemAccountService.selectCurrent();
				systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(accountManagermentCharge));
				systemAccountService.update(systemAccount);
				systemAccountFlowService.createGainAccountManagermentChargeFlow(systemAccount, accountManagermentCharge);

				//		对于投资人的操作
				//遍历bidRequest.getBids()获取到投资列表
				//遍历投标对象
				//投资人冻结金额减少
				//增加投资成功的流水
				//投资人的代收本金和待收利息增加-->后续算出来的
				//更新投资人账号
				List<Bid> bids = bidRequest.getBids();
				Map<Long, Account> accountMap = new HashMap<>();
				Long bidUserId;
				Account bidUserAccount;
				for (Bid bid : bids) {
					bidUserId = bid.getBidUser().getId();
					bidUserAccount = accountMap.get(bidUserId);
					//查询出每个投资对象.
					if ( bidUserAccount == null ) {
						bidUserAccount = accountService.get(bidUserId);
						accountMap.put(bidUserId, bidUserAccount);
					}
					//可用金额增加,冻结金额减少.
					bidUserAccount.setFreezedAmount(bidUserAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
					bidUserAccount.setUsableAmount(bidUserAccount.getUsableAmount().add(bid.getAvailableAmount()));
					//增加投资成功的流水
					accountFlowService.createBidSuccessFlow(bidUserAccount, bid.getAvailableAmount());
				}

				//还款对象和还款明细对象
				List<PaymentSchedule> pss = createPaymentScheduleList(bidRequest);
				//计算投资人的代收利息和代收本金
				for (PaymentSchedule ps : pss) {
					for (PaymentScheduleDetail psd : ps.getDetails()) {
						bidUserAccount = accountMap.get(psd.getInvestorId());
						bidUserAccount.setUnReceivePrincipal(bidUserAccount.getUnReceivePrincipal().add(psd.getPrincipal()));
						bidUserAccount.setUnReceiveInterest(bidUserAccount.getUnReceiveInterest().add(psd.getInterest()));
					}
				}
				//对 账户统一的修改
				for (Account account : accountMap.values()) {
					accountService.update(account);
				}
			} else {
				//如果审核失败
				//设置借款对象的的状态--->满标审核拒绝
				//设置投标对象的状态---->满标审核拒绝
				//	遍历投标集合对象
				//	找出对应的投资人,可用金额增加,冻结金额减少
				//	生成对应的流水信息
				//移除正在借款的流程状态码
				//更新借款用户userinfo对象
				//	}
				//更新借款对象
				bidRejectFlow(bidRequest);
			}
			this.update(bidRequest);
		}

	}

	private List<PaymentSchedule> createPaymentScheduleList(BidRequest bidRequest) {
		List<PaymentSchedule> pss = new ArrayList<>();
		PaymentSchedule ps;
		BigDecimal interestTemp = BidConst.ZERO;
		BigDecimal principalTemp = BidConst.ZERO;
		for (int i = 0; i < bidRequest.getMonthes2Return(); i++) {
			ps = new PaymentSchedule();
			ps.setBidRequestId(bidRequest.getId());//关联借款id
			ps.setBidRequestTitle(bidRequest.getTitle()); //关联借款的标题
			ps.setBidRequestType(bidRequest.getBidRequestType()); //借款的类型
			ps.setBorrowUser(bidRequest.getCreateUser());//关联借款人
			ps.setMonthIndex(i + 1); //还款的期数
			ps.setDeadLine(DateUtils.addMonths(bidRequest.getPublishTime(), i + 1));//还款截止时间
			ps.setReturnType(bidRequest.getReturnType());
			//判断是否最后一期还款
			if ( i < bidRequest.getMonthes2Return() - 1 ) {
				ps.setTotalAmount(CalculatetUtil.calMonthToReturnMoney(bidRequest.getReturnType(),//还款的方式
						bidRequest.getBidRequestAmount(),//还款的金额
						bidRequest.getCurrentRate(),//还款的年利率
						i + 1,//第几期还款
						bidRequest.getMonthes2Return()//还款的期数
				));
				ps.setInterest(CalculatetUtil.calMonthlyInterest(bidRequest.getReturnType(),//还款的方式
						bidRequest.getBidRequestAmount(),//还款的金额
						bidRequest.getCurrentRate(),//还款的年利率
						i + 1,//第几期还款
						bidRequest.getMonthes2Return()//还款的期数
				));
				//该期本金=该期还款金额-该期利息
				ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));
				interestTemp = interestTemp.add(ps.getInterest());
				principalTemp = principalTemp.add(ps.getPrincipal());
			} else {
				//最后一期
				//该期本金=借款的本金-前n-1期的本金之和
				ps.setPrincipal(bidRequest.getBidRequestAmount().subtract(principalTemp));
				//该期利息=借款的利息-前n-1期的利息之和
				ps.setInterest(bidRequest.getBidRequestAmount().subtract(interestTemp));
				//该期还款金额=该期本金+该期利息
				ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));

			}
			paymentScheduleService.save(ps);
			createPaymentScheduleDetail(ps,bidRequest);
			pss.add(ps);
		}
		return pss;
	}

	private void createPaymentScheduleDetail(PaymentSchedule ps, BidRequest bidRequest) {
		PaymentScheduleDetail psd;
		Bid bid;
		BigDecimal interestTemp = BidConst.ZERO;
		BigDecimal principalTemp = BidConst.ZERO;
		for (int i = 0; i < bidRequest.getBids().size(); i++) {
			bid = bidRequest.getBids().get(i);
			psd = new PaymentScheduleDetail();

			psd.setBidAmount(bid.getAvailableAmount());//还款明细对象设置投标金额
			psd.setBidId(bid.getId()); //关联投标对象
			psd.setBidRequestId(bidRequest.getId());//关联借款对象
			psd.setBorrowUser(bidRequest.getCreateUser());//管理借款人
			psd.setDeadLine(ps.getDeadLine());//设置还款截止时间
			psd.setInvestorId(bid.getBidUser().getId());//关联投资人id
			psd.setMonthIndex(ps.getMonthIndex());//第几期的还款对象
			psd.setPaymentScheduleId(ps.getId());//管理还款对象
			psd.setReturnType(ps.getReturnType());//还款方式

			if ( i < bidRequest.getBids().size() -1 ) {
				//不是最后一个投标人
				BigDecimal bidRate = bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(),BidConst.CAL_SCALE, RoundingMode.HALF_UP);
				//该还款明细的本金 = (该次投标 / 借款金额) * 该期还款的本金
				psd.setPrincipal(bidRate.multiply(ps.getPrincipal()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
				//该还款明细的利息 = (该次投标 / 借款金额) * 该期还款的利息
				psd.setInterest(bidRate.multiply(ps.getInterest()).setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP));
				//该还款明细的总金额 = 该明细的本金 + 该明细的利息
				psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
				principalTemp = principalTemp.add(psd.getPrincipal());
				interestTemp = interestTemp.add(psd.getInterest());
			}else {
				//最后一个投标人
				//该还款明细的本金=该还款的本金 - (前 n-1个投资人的本金之和)
				psd.setPrincipal(ps.getPrincipal().subtract(principalTemp));
				//还款明细的利息=该还款的利息 - (前 n-1个投资人的利息之和)
				psd.setInterest(ps.getInterest().subtract(interestTemp));
				//该还款明细的总金额 = 该还款明细的本金 + 该还款明细的利息
				psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
			}
			paymentScheduleDetailService.save(psd);
			ps.getDetails().add(psd);
		}
	}
}

































package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.domain.AccountFlow;
import com._163.king13zhi.p2p.business.mapper.AccountFlowMapper;
import com._163.king13zhi.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kingdan on 2018/1/27.
 */
@Service@Transactional
public class AccountFlowServiceImpl implements IAccountFlowService {
	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Override
	public int save(AccountFlow accountFlow) {
		return accountFlowMapper.insert(accountFlow);
	}

	@Override
	public void createRechargeOfflineFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,"线下充值成功:"+amount+"元");
	}

	@Override
	public void createBidFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED,"投标冻结:"+amount+"元");
	}

	@Override
	public void createBidFaileFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,"投标失败,取消冻结:"+amount+"元");
	}

	@Override
	public void createBidRequestSuccessFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL,"借款成功:"+amount+"元");
	}

	@Override
	public void createSystemAccountSuccessFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_CHARGE,"支付平台借款手续费:"+amount+"元");
	}

	@Override
	public void createBidSuccessFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL,"投资成功:"+amount+"元");
	}

	@Override
	public void createReturnMoneyFlow(Account account, BigDecimal amount) {
		createFlow(account,amount, BidConst.ACCOUNT_ACTIONTYPE_RETURN_MONEY,"还款成功:"+amount+"元");
	}

	@Override
	public void createGainPrincipalAndInteresFlow(Account bidUserAccount, BigDecimal amount) {
		createFlow(bidUserAccount,amount, BidConst.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY,"回款成功:"+amount+"元");
	}

	@Override
	public void createPayInterestManagerChargeFlow(Account bidUserAccount, BigDecimal amount) {
		createFlow(bidUserAccount,amount, BidConst.ACCOUNT_ACTIONTYPE_INTEREST_SHARE,"平台利息管理费:"+amount+"元");

	}

	public void createFlow(Account account, BigDecimal amount,int actionType,String remark) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(account.getId());
		flow.setAmount(amount);
		flow.setTradeTime(new Date());
		flow.setUsableAmount(account.getUnReturnAmount());
		flow.setFreezedAmount(account.getFreezedAmount());
		flow.setActionType(actionType);
		flow.setRemark(remark);
		this.save(flow);
	}

}

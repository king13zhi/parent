package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.business.domain.AccountFlow;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/1/27.
 */
public interface IAccountFlowService {
	int save(AccountFlow accountFlow);

	/**
	 * 充值成功的流水
	 * @param applierAccount
	 * @param amount
	 */
	void createRechargeOfflineFlow(Account applierAccount, BigDecimal amount);

	/**
	 * 投标冻结流水
	 * @param account
	 * @param amount
	 */
	void createBidFlow(Account account, BigDecimal amount);

	/**
	 * 投标失败的流水
	 * @param account
	 * @param amount
	 */
	void createBidFaileFlow(Account account, BigDecimal amount);

	/**
	 * 借款成功的流水
	 * @param account
	 * @param amount
	 */
	void createBidRequestSuccessFlow(Account account, BigDecimal amount);

	/**
	 * 系统账户流水记录
	 * @param account
	 * @param amount
	 */
	void createSystemAccountSuccessFlow(Account account, BigDecimal amount);

	/**
	 * 投资成功的流水
	 * @param bidUserAccount
	 * @param availableAmount
	 */
	void createBidSuccessFlow(Account account, BigDecimal amount);


	/**
	 * 还款的流水
	 * @param account
	 * @param totalAmount
	 */
	void createReturnMoneyFlow(Account account, BigDecimal amount);

	/**
	 * 回款流水
	 * @param bidUserAccount
	 * @param amount
	 */
	void createGainPrincipalAndInteresFlow(Account bidUserAccount, BigDecimal amount);

	/**
	 * 支付平台利息管理费
	 * @param bidUserAccount
	 * @param interestManagerCharge
	 */
	void createPayInterestManagerChargeFlow(Account bidUserAccount, BigDecimal amount);
}

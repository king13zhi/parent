package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.SystemAccount;
import com._163.king13zhi.p2p.business.domain.SystemAccountFlow;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/1/28.
 */
public interface ISystemAccountFlowService {
	int save(SystemAccountFlow systemAccountFlow);


	/**
	 * 系统账户收取用户的借款手续费
	 * @param systemAccount
	 * @param amount
	 */
	void createGainAccountManagermentChargeFlow(SystemAccount systemAccount, BigDecimal amount);

	/**
	 * 抽取利息管理费
	 * @param systemAccount
	 * @param interestManagerCharge
	 */
	void createGainInterestManagerChargeFlow(SystemAccount systemAccount, BigDecimal amount);

}

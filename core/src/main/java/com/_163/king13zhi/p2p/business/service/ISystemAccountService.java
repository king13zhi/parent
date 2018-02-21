package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.SystemAccount;

/**
 * Created by kingdan on 2018/1/28.
 */
public interface ISystemAccountService {
	int save(SystemAccount systemAccount);
	int update(SystemAccount systemAccount);
	SystemAccount selectCurrent();
	void initSystemAccount();

}

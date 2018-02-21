package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.business.domain.SystemAccount;
import com._163.king13zhi.p2p.business.mapper.SystemAccountMapper;
import com._163.king13zhi.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingdan on 2018/1/28.
 */
@Service@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService {
	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Override
	public int save(SystemAccount systemAccount) {
		return systemAccountMapper.insert(systemAccount);
	}

	@Override
	public int update(SystemAccount systemAccount) {
		return systemAccountMapper.updateByPrimaryKey(systemAccount);
	}

	@Override
	public SystemAccount selectCurrent() {
		return systemAccountMapper.selectCurrent();
	}

	@Override
	public void initSystemAccount() {
		SystemAccount systemAccount = systemAccountMapper.selectCurrent();
		if ( systemAccount == null ) {
			systemAccount = new SystemAccount();
			this.save(systemAccount);
		}
	}
}

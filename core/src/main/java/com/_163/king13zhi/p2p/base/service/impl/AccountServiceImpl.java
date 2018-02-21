package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.mapper.AccountMapper;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingdan on 2018/1/18.
 */
@Service@Transactional
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public int save(Account account) {
		return accountMapper.insert(account);
	}

	@Override
	public int update(Account account) {
		int count = accountMapper.updateByPrimaryKey(account);
		//更新操作方法一般都涉及到乐观锁的问题,为了性能所以一般都使用添加一个version来进行间接的控制.
		if (count == 0) {
			throw new RuntimeException("乐观锁异常,accountId:" + account.getId());
		}
		return count;
	}

	@Override
	public Account get(Long id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	@Override
	public Account getCurrent() {
		return this.get(UserContext.getCurrent().getId());
	}

}

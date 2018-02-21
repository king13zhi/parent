package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.Account;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IAccountService {

	/**
	 * 账户的保存方法
	 * @param account
	 * @return 操作成功受影响的行数
	 */
	int save(Account account);

	/**
	 * 账户的更新方法
	 * @param account
	 * @return 操作成功受影响的行数
	 */
	int update(Account account);

	/**
	 * 账户的单独查询方法
	 * @param id
	 * @return 方法单个Account对象
	 */
	Account get(Long id);

	Account getCurrent();

}

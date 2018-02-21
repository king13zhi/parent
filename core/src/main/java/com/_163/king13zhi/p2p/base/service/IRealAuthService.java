package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.RealAuth;
import com._163.king13zhi.p2p.base.query.RealAuthQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by kingdan on 2018/1/23.
 */
public interface IRealAuthService {
	int save(RealAuth realAuth);
	int update(RealAuth realAuth);
	RealAuth get(Long id);

	/**
	 * 实名认证的方法
	 * @param realAuth
	 */
	void realAuthSave(RealAuth realAuth);

	/**
	 * 实名认证审核
	 * @param id
	 * @param state
	 * @param remark
	 */
	void audit(Long id, int state, String remark);

	/**
	 * 实名认证分页
	 * @param qo
	 * @return
	 */
	PageInfo queryPage(RealAuthQueryObject qo);
}

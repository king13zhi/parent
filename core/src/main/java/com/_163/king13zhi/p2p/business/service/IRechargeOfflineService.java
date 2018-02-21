package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.RechargeOffline;
import com._163.king13zhi.p2p.business.query.RechargeOfflineQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by kingdan on 2018/1/27.
 */

public interface IRechargeOfflineService {
	int save(RechargeOffline rechargeOffline);
	int update(RechargeOffline rechargeOffline);
	RechargeOffline get(Long id);

	void rechargeSave(RechargeOffline rechargeOffline);

	PageInfo pagePage(RechargeOfflineQueryObject qo);

	/**
	 * 线下充值审核
	 * @param id
	 * @param state
	 * @param remark
	 */
	void audit(Long id, int state, String remark);
}

package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.Bid;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IBidService {

	/**
	 * 账户的保存方法
	 * @param bid
	 * @return 操作成功受影响的行数
	 */
	int save(Bid bid);

	/**
	 * 账户的更新方法
	 * @param bid
	 * @return 操作成功受影响的行数
	 */
	int update(Bid bid);

	/**
	 * 账户的单独查询方法
	 * @param id
	 * @return 方法单个Bid对象
	 */
	Bid get(Long id);

	/**
	 * 批量修改投标的状态
	 * @param bidRequestId
	 * @param bidRequestState
	 */
	void updateState(Long bidRequestId, int bidRequestState);
}

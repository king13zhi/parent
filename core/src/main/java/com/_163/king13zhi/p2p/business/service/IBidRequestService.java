package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.business.domain.BidRequest;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IBidRequestService {

	/**
	 * 账户的保存方法
	 * @param bidRequest
	 * @return 操作成功受影响的行数
	 */
	int save(BidRequest bidRequest);

	/**
	 * 账户的更新方法
	 * @param bidRequest
	 * @return 操作成功受影响的行数
	 */
	int update(BidRequest bidRequest);

	/**
	 * 账户的单独查询方法
	 * @param id
	 * @return 方法单个Bid对象
	 */
	BidRequest get(Long id);

	boolean canApplyBorrow(Userinfo userinfo);

	void apply(BidRequest bidRequest);

	PageInfo queryPage(BidRequestQueryObject qo);

	/**
	 * 发标前审核
	 * @param id
	 * @param state
	 * @param remark
	 */
	void publishAudit(Long id, int state, String remark);

	List<BidRequest> queryIndexList(BidRequestQueryObject qo);

	/**
	 * 投标前审核
	 * @param bidRequestId
	 * @param amount
	 */
	void bid(Long bidRequestId, BigDecimal amount);

	/**
	 * 满标一审
	 * @param id
	 * @param state
	 * @param remark
	 */
	void bidrequestAudit1(Long id, int state, String remark);

	/**
	 * 满标二审
	 * @param id
	 * @param state
	 * @param remark
	 */
	void bidrequestAudit2(Long id, int state, String remark);
}

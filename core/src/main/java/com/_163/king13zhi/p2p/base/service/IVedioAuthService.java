package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.VedioAuth;
import com._163.king13zhi.p2p.base.query.VideoAuthQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IVedioAuthService {

	/**
	 * 数据字典的保存方法
	 * @param vedioAuth
	 * @return 操作成功受影响的行数
	 */
	int save(VedioAuth vedioAuth);

	/**
	 * 数据字典的更新方法
	 * @param vedioAuth
	 * @return 操作成功受影响的行数
	 */
	int update(VedioAuth vedioAuth);

	/**
	 * id的查询方法
	 * @param id
	 * @return
	 */
	VedioAuth get(Long id);

	PageInfo queryPageHelper(VideoAuthQueryObject qo);

	/**
	 * 视频审核信息
	 * @param applierId
	 * @param state
	 * @param remark
	 */
	void audit(Long applierId, int state, String remark);
}

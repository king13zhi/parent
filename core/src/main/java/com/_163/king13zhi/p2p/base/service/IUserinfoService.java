package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.Userinfo;

import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IUserinfoService {
	/**
	 * 用户个人中心的保存方法
	 * @param account
	 * @return 操作成功受影响的行数
	 */
	int save(Userinfo userinfo);

	/**
	 * 用户个人中心的更新方法
	 * @param account
	 * @return 操作成功受影响的行数
	 */
	int update(Userinfo userinfo);

	/**
	 * 用户个人中心单独查询方法
	 * @param account
	 * @return 方法单个Userinfo对象
	 */
	Userinfo get(Long id);

	Userinfo getCurrent();

	/**
	 * 手机绑定方法
	 * @param phoneNumber
	 * @param verifyCode
	 */
	void bindPhone(String phoneNumber, String verifyCode);

	/**
	 * 邮箱绑定
	 * @param key
	 */
	void bindEmail(String key);

	/**
	 * 填写个人资料
	 * @param userinfo
	 */
	void basicInfoSave(Userinfo userinfo);



}

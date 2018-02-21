package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.Logininfo;

import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface ILogininfoService {
	/**
	 * 注册成功后保存入库的方法(后台现在传入的时username,password)
	 *
	 * @param username 用户名
	 * @param password 用户密码
	 * @return Logininfo对象
	 */
	Logininfo register(String username, String password);

	/**
	 * 登录用户名是否可注册验证方法
	 * @param username
	 * @return true/false
	 */
	boolean queryUsername(String username);

	/**
	 * web用户登录个人中心验证方法
	 * @param username
	 * @param password
	 * @param userType
	 * @return 返回Logininfo
	 */
	Logininfo login(String username, String password, int userType);

	/**
	 * 初始化系统管理员方法
	 */
	void initAdmin();

	/**
	 * 查询人自动补全(也就是高级查询)
	 * @param keyword
	 * @return
	 */
	List<Map<String,Object>> queryAutoComplete(String keyword);


}

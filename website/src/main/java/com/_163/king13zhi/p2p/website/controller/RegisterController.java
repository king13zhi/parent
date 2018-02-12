package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.service.ILogininfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/17.
 */

@Controller
public class RegisterController {
	@Autowired
	private ILogininfoService logininfoService;

	/**
	 * web用户注册方法
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/userRegister")
	@ResponseBody
	public AjaxResult register(String username, String password) {
		try {
			logininfoService.register(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}


	/**
	 * web用户注册时用户名是否合法等验证
	 * @param username
	 * @return
	 */
	@RequestMapping("queryUsername")
	@ResponseBody
	public boolean queryUsername(String username) {
		boolean b = logininfoService.queryUsername(username);
		System.out.println(b);
		return b;
	}

	/**
	 * 用户登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public AjaxResult login(String username, String password) {
		Logininfo logininfo = logininfoService.login(username, password,Logininfo.USERTYPE_USER);
		//因为service中存在日志保存操作(成功和失败都要进行保存,失败的话事物会回滚),所以在这里进行
		//验证处理
		if (logininfo == null) {
			return new AjaxResult("验证失败");
		} else {
			return new AjaxResult(true, "验证成功");
		}
	}
}

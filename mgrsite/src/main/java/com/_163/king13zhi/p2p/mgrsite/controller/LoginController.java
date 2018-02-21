package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.service.ILogininfoService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/20.
 */
@Controller
public class LoginController {
	@Autowired
	private ILogininfoService logininfoService;
	@Autowired
	private IUserinfoService userinfoService;

	/**
	 * 后台登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/managerLogin")
	@ResponseBody
	public AjaxResult managerLogin(String username, String password) {
		Logininfo logininfo = logininfoService.login(username, password, Logininfo.USERTYPE_MANAGER);
		if (logininfo == null) {
			return new AjaxResult("登录失败");
		} else {
			return new AjaxResult(true, "验证成功");
		}
	}

	/**
	 * 跳转主页面
	 *
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "main";
	}

}

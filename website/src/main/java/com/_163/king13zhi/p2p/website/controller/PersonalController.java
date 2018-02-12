package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.website.annotion.RequireLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/17.
 */

@Controller
public class PersonalController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserinfoService userinfoService;

	/**
	 * 访问个人中心页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/personal")
	@RequireLogin   //表明该方法需要登录才能进行访问
	public String personalPage(Model model) {
		model.addAttribute("account", accountService.getCurrent());
		model.addAttribute("userinfo", userinfoService.getCurrent());
		model.addAttribute("date", new Date());
		return "personal";
	}

	@RequestMapping("/bindPhone")
	@ResponseBody
	public AjaxResult bindPhone(String phoneNumber, String verifyCode) {
		try {
			userinfoService.bindPhone(phoneNumber, verifyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}

	@RequestMapping("/bindEmail")
	public String bindEmail(String key, Model model) {
		try {
			userinfoService.bindEmail(key);
			model.addAttribute("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("success", false);
			model.addAttribute("msg", e.getMessage());
		}
		return "checkmail_result";
	}
}

package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.service.IVerifyCodeService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/17.
 */

@Controller
public class VerifyCodeController {
	@Autowired
	private IVerifyCodeService verifyCodeService;

	@RequestMapping("/sendverifyCode")
	@ResponseBody
	public AjaxResult sendVerifyCode(String phoneNumber) {

		try {
			verifyCodeService.sendVerifyCode(phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		//这里的业务逻辑有点问题
		return new AjaxResult(true, "验证成功");
	}
}

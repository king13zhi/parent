package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.service.IEmailService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/21.
 */
@Controller
public class EmailController {
	@Autowired
	private IEmailService emailService;

	@RequestMapping("/sendEmail")
	@ResponseBody
	public AjaxResult sendEmail(String email) {
		try {
			emailService.sendEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}

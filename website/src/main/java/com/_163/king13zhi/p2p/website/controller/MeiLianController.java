package com._163.king13zhi.p2p.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/21.
 */
@Controller
public class MeiLianController {
	@RequestMapping("/sendSms")
	@ResponseBody
	public String sendSms(String username,String password,String apiKey,String phoneNumber,String content) {
		System.out.println("username:" + username);
		System.out.println("password:" + password);
		System.out.println("apiKey:" + apiKey);
		System.out.println("phoneNumber:" + phoneNumber);
		System.out.println("content:" + content);
		return "success";
	}
}






























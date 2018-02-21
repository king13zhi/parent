package com._163.king13zhi.p2p.base.service;

/**
 * Created by kingdan on 2018/1/21.
 */
public interface IVerifyCodeService {
	/**
	 * 发送短信
	 * @param phoneNumber 手机号码
	 */
	void sendVerifyCode(String phoneNumber);

	boolean validate(String phoneNumber, String verifyCode);
}

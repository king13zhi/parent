package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.MailVerify;

/**
 * Created by kingdan on 2018/1/21.
 */
public interface IMailVerifyService {
	int save(MailVerify mailVerify);
	MailVerify get(String uuid);
}

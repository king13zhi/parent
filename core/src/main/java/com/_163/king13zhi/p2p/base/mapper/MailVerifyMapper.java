package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.MailVerify;

public interface MailVerifyMapper {
	int insert(MailVerify record);
	MailVerify selectByUUID(String uuid);
}
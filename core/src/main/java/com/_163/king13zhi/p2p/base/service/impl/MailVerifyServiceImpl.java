package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.MailVerify;
import com._163.king13zhi.p2p.base.mapper.MailVerifyMapper;
import com._163.king13zhi.p2p.base.service.IMailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingdan on 2018/1/21.
 */
@Service@Transactional
public class MailVerifyServiceImpl implements IMailVerifyService {
	@Autowired
	private MailVerifyMapper mailVerifyMapper;

	@Override
	public int save(MailVerify mailVerify) {
		return mailVerifyMapper.insert(mailVerify);
	}

	@Override
	public MailVerify get(String uuid) {
		return mailVerifyMapper.selectByUUID(uuid);
	}
}

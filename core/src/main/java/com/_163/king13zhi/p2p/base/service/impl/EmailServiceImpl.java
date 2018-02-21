package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.MailVerify;
import com._163.king13zhi.p2p.base.service.IEmailService;
import com._163.king13zhi.p2p.base.service.IMailVerifyService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

/**
 * Created by kingdan on 2018/1/21.
 */
@Service@Transactional
public class EmailServiceImpl implements IEmailService {
	//java发送mail实现类
	@Autowired
	private JavaMailSender sender;

	@Value("$(email.applicationUrl")
	private String applicationUrl;
	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private IMailVerifyService mailVerifyService;

	@Override
	public void sendEmail(String email) {
		//构建UUID,拼接在地址栏的参数
		String uuid = UUID.randomUUID().toString();
		//构建邮件内容
		StringBuilder msg = new StringBuilder(100);
		msg.append("感谢注册p2p平台,这是你的认证邮件,点击<a href='").append(applicationUrl).append("/bindEmail?key=")
				.append(uuid).append("'>这里</a>完后认证,有效期为").append(BidConst.EMAIL_VAILD_TIME).append("天,请尽快认证");

		//------------------------------------------
		//把发送的邮件地址,发送人id,发送时间,保存到数据库.
		try {
			MailVerify mailVerify  = new MailVerify();
			mailVerify.setSendTime(new Date());
			mailVerify.setEmail(email);
			mailVerify.setUuid(uuid);
			mailVerify.setUserId(UserContext.getCurrent().getId());
			mailVerifyService.save(mailVerify);
			sendEmail(email,msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//springmail相关格式设置和封装
	private void sendEmail(String email, String content) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
		helper.setSubject("这是一封验证邮件");
		helper.setTo(email);
		helper.setFrom(fromEmail);
		helper.setText(content, true);
		sender.send(message);
	}
}






























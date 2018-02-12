package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/21.
 */
@Setter@Getter
public class MailVerify extends BaseDomain { //邮件认证相关实体类
	private String email;
	private Long userId;
	private Date sendTime;
	private String uuid;
}

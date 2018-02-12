package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter @Getter
public class IpLog extends BaseDomain {
	//登录成功
	public static final int LOGIN_SUCCESS=0;
	//登录失败
	public static final int LOGIN_FAILED = 1;

	//前台用户
	public static final int USERTYPE_USER= 1;
	//后台用户
	public static final int USERTYPE_MANAGER= 0;

	private String ip;
	private String username;
	private Date loginTime;
	private int state;

	public String getStateDisplay(){
		return this.state == LOGIN_SUCCESS ? "登录成功" : "登录失败";
	}

	//web用户和管理用户日志状态
	private int userType;
}

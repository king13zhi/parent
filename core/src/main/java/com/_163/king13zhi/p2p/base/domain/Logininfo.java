package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class Logininfo extends BaseDomain {
	public static final int STATE_NORMAL = 0;//正常状态
	public static final int STATE_LOCK = 1;//锁定状态

	public static final int USERTYPE_USER = 1;//普通用户
	public static final int USERTYPE_MANAGER = 0;//管理员用户

	private String username;
	private String password;
	private int state;
	//用户状态(web用户和管理用户)
	private int userType;
}
package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class ${className} extends BaseDomain {
	public static final int STATE_NORMAL = 0;//正常状态
	public static final int STATE_LOCK = 1;//锁定状态
	private String username;
	private String password;
	private int state;
}
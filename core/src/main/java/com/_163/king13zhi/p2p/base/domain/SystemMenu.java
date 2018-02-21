package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SystemMenu extends BaseDomain{
	private String text;
	private String iconCls;
	private String url;
	private Long parent_id;
}
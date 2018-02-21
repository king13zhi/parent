package com._163.king13zhi.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemdictionaryitemQueryObject extends QueryObject {
	private String keyword;
	private Long parentId;
}

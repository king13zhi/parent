package com._163.king13zhi.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by kingdan on 2018/1/20.
 */

/**
 * 统一用户查询参数封装
 */
@Setter
@Getter
public class QueryObject {
	private Integer currentPage = 1;
	private Integer pageSize = 4;
}

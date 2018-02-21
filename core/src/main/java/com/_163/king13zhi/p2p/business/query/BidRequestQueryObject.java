package com._163.king13zhi.p2p.business.query;

import com._163.king13zhi.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kingdan on 2018/1/26.
 */
@Setter@Getter
public class BidRequestQueryObject extends QueryObject {
	private int bidRequestState = -1;
	private int[] states;
	private String orderByCondition;
}

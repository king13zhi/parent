package com._163.king13zhi.p2p.business.domain;

import com._163.king13zhi.p2p.base.domain.BaseAuthDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kingdan on 2018/1/26.
 */
@Setter
@Getter
public class BidRequestAuditHistory extends BaseAuthDomain {
	public static final int PUBLISH_AUDIT = 0; //发标前审核
	public static final int AUDIT1 = 1; //1审
	public static final int AUDIT2 = 2; //2审

	private Long bidRequestId;
	private int auditType;

	public String getDisplayAuditType() {
		switch (this.auditType) {
			case PUBLISH_AUDIT:
				return "发标前审核";
			case AUDIT1:
				return "满标一审";
			case AUDIT2:
				return "满标二审";
			default:
				return "";
		}
	}
}

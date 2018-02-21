package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.BidRequestAuditHistory;

import java.util.List;

/**
 * Created by kingdan on 2018/1/26.
 */
public interface IBidRequestAuditHistoryService {
	int save(BidRequestAuditHistory bidRequestAuditHistory);

	List<BidRequestAuditHistory> queryByBidRequestId(Long id);
}

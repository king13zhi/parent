package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.BidRequestAuditHistory;
import java.util.List;

public interface BidRequestAuditHistoryMapper {
    int insert(BidRequestAuditHistory record);

	List<BidRequestAuditHistory> queryByBidRequestId(Long bidRequestId);
}
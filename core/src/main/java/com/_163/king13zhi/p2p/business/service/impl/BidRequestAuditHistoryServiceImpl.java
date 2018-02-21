package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.business.domain.BidRequestAuditHistory;
import com._163.king13zhi.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com._163.king13zhi.p2p.business.service.IBidRequestAuditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingdan on 2018/1/26.
 */
@Service@Transactional
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
	@Autowired
	private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

	@Override
	public int save(BidRequestAuditHistory bidRequestAuditHistory) {
		return bidRequestAuditHistoryMapper.insert(bidRequestAuditHistory);
	}

	@Override
	public List<BidRequestAuditHistory> queryByBidRequestId(Long id) {
		return bidRequestAuditHistoryMapper.queryByBidRequestId(id);
	}
}

package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.business.domain.Bid;
import com._163.king13zhi.p2p.business.mapper.BidMapper;
import com._163.king13zhi.p2p.business.service.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingdan on 2018/1/25.
 */
@Service@Transactional
public class BidServiceImpl implements IBidService {
	@Autowired
	private BidMapper bidMapper;
	
	@Override
	public int save(Bid bid) {
		return bidMapper.insert(bid);
	}

	@Override
	public int update(Bid bid) {
		return bidMapper.updateByPrimaryKey(bid);
	}

	@Override
	public Bid get(Long id) {
		return bidMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateState(Long bidRequestId, int bidRequestState) {
		bidMapper.updateSate(bidRequestId,bidRequestState);
	}
}

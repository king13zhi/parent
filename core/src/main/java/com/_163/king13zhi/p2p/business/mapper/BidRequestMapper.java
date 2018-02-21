package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.BidRequest;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {
    int insert(BidRequest record);
    BidRequest selectByPrimaryKey(Long id);
    int updateByPrimaryKey(BidRequest record);

	List queryPage(BidRequestQueryObject qo);

}
package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.Bid;
import org.apache.ibatis.annotations.Param;

public interface BidMapper {
    int insert(Bid record);
    Bid selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Bid record);

	void updateSate(@Param("bidRequestId") Long bidRequestId, @Param("bidRequestState") int bidRequestState);
}
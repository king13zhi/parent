package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.RechargeOffline;
import com._163.king13zhi.p2p.business.query.RechargeOfflineQueryObject;

import java.util.List;

public interface RechargeOfflineMapper {
    int insert(RechargeOffline record);
    RechargeOffline selectByPrimaryKey(Long id);
    int updateByPrimaryKey(RechargeOffline record);

	List pagePage(RechargeOfflineQueryObject qo);
}
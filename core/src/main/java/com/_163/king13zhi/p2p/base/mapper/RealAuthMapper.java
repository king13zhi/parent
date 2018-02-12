package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.RealAuth;
import com._163.king13zhi.p2p.base.query.RealAuthQueryObject;

import java.util.List;

public interface RealAuthMapper {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

	List queryPage(RealAuthQueryObject qo);
}
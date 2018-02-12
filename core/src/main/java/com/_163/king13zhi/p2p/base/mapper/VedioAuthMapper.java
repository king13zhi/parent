package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.VedioAuth;
import com._163.king13zhi.p2p.base.query.VideoAuthQueryObject;

import java.util.List;

public interface VedioAuthMapper {
    int insert(VedioAuth record);
    VedioAuth selectByPrimaryKey(Long id);
    List<VedioAuth> selectAll();
    int updateByPrimaryKey(VedioAuth record);

	List queryPageDataHelper(VideoAuthQueryObject qo);
}
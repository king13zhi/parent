package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.Userinfo;

public interface UserinfoMapper {
    int insert(Userinfo record);
    Userinfo selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Userinfo record);


}
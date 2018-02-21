package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.PlatformBankinfo;
import com._163.king13zhi.p2p.business.query.PlatformBankinfoQueryObject;

import java.util.List;

public interface PlatformBankinfoMapper {
    int insert(PlatformBankinfo record);
    PlatformBankinfo selectByPrimaryKey(Long id);
    List<PlatformBankinfo> selectAll();
    int updateByPrimaryKey(PlatformBankinfo record);

	List queryPage(PlatformBankinfoQueryObject qo);

}
package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {
    int insert(SystemAccount record);
    SystemAccount selectCurrent();
    int updateByPrimaryKey(SystemAccount record);
}
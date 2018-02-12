package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.Account;

public interface AccountMapper {
    int insert(Account record);
    Account selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Account record);
}
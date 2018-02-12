package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.IpLog;
import com._163.king13zhi.p2p.base.query.IplogQueryObject;

import java.util.List;

public interface IpLogMapper {
	int insert(IpLog record);

	List queryPageDataHelper(IplogQueryObject qo);

}
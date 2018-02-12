package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogininfoMapper {
	//只留下insert的理由是因为数据都是宝贵的所以不做其它操作.
	int insert(Logininfo record);

	int queryCountByUsername(String username);

	Logininfo selectLogininfoByUsrenameAndPassword(@Param("username") String username, @Param("password") String password, @Param("userType") int userType);

	int queryCountByUserType(int userType);

	List<Map<String,Object>> autocomplate(String keyword);

}
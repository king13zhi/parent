package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.SystemMenu;

import java.util.List;

public interface SystemMenuMapper {
	int deleteByPrimaryKey(Long id);

	int insert(SystemMenu record);

	SystemMenu selectByPrimaryKey(Long id);

	List<SystemMenu> selectAll();

	int updateByPrimaryKey(SystemMenu record);

	List<SystemMenu> selectMenuByParentId(Long parentId);

}
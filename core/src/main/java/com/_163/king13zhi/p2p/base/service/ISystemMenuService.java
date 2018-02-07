package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.SystemMenu;

import java.util.List;

/**
 * Created by kingdan on 2018/2/7.
 */
public interface ISystemMenuService {
	/**
	 * 查询菜单
	 * @param parentId
	 * @return
	 */
	List<SystemMenu> loadMenuByParentIdIsNull(Long parentId);

}

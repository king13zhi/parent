package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.SystemMenu;
import com._163.king13zhi.p2p.base.mapper.SystemMenuMapper;
import com._163.king13zhi.p2p.base.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingdan on 2018/2/7.
 */
@Service
public class SystemMenuServiceImpl implements ISystemMenuService {
	@Autowired
	private SystemMenuMapper systemmenuMapper;

	@Override
	public List<SystemMenu> loadMenuByParentIdIsNull(Long parentId) {
		return systemmenuMapper.selectMenuByParentId(parentId);
	}


}

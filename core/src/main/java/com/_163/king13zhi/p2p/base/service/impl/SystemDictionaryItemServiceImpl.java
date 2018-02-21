package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Systemdictionaryitem;
import com._163.king13zhi.p2p.base.mapper.SystemDictionaryItemMapper;
import com._163.king13zhi.p2p.base.query.SystemdictionaryitemQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingdan on 2018/1/18.
 */
@Service@Transactional
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
	@Autowired
	private SystemDictionaryItemMapper systemdictionaryitemMapper;

	@Override
	public int save(Systemdictionaryitem systemdictionaryitem) {
		return systemdictionaryitemMapper.insert(systemdictionaryitem);
	}

	@Override
	public int update(Systemdictionaryitem systemdictionaryitem) {
		return systemdictionaryitemMapper.updateByPrimaryKey(systemdictionaryitem);
	}

	@Override
	public Systemdictionaryitem get(Long id) {
		return systemdictionaryitemMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Systemdictionaryitem> queryListBySn(String sn) {
		return systemdictionaryitemMapper.queryListBySn(sn);
	}

	@Override
	public PageInfo queryPageHelper(SystemdictionaryitemQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
		List data = systemdictionaryitemMapper.queryPageDataHelper(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}

	@Override
	public void saveOrUpdate(Systemdictionaryitem systemdictionaryitem) {
		if (systemdictionaryitem.getId() == null) {
			this.save(systemdictionaryitem);
		}else {
			this.update(systemdictionaryitem);
		}
	}
}

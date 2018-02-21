package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Systemdictionary;
import com._163.king13zhi.p2p.base.mapper.SystemDictionaryMapper;
import com._163.king13zhi.p2p.base.query.SystemdictionaryQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryService;
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
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
	@Autowired
	private SystemDictionaryMapper systemdictionaryMapper;

	@Override
	public int save(Systemdictionary systemdictionary) {
		return systemdictionaryMapper.insert(systemdictionary);
	}

	@Override
	public int update(Systemdictionary systemdictionary) {
		return systemdictionaryMapper.updateByPrimaryKey(systemdictionary);
	}

	@Override
	public Systemdictionary get(Long id) {
		return systemdictionaryMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo queryPageHelper(SystemdictionaryQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
		List data = systemdictionaryMapper.queryPageDataHelper(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}

	//根据是否有id判断是更新还是新增
	@Override
	public void saveOrUpdate(Systemdictionary systemdictionary) {
		if (systemdictionary.getId() == null) {
			this.save(systemdictionary);
		}else {
			this.update(systemdictionary);
		}
	}

	@Override
	public List<Systemdictionary> selectAll() {
		return systemdictionaryMapper.selectAll();
	}
}

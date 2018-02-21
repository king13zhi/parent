package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.business.domain.PlatformBankinfo;
import com._163.king13zhi.p2p.business.mapper.PlatformBankinfoMapper;
import com._163.king13zhi.p2p.business.query.PlatformBankinfoQueryObject;
import com._163.king13zhi.p2p.business.service.IPlatformBankinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingdan on 2018/1/27.
 */
@Service@Transactional
public class PlatformBankinfoServiceImpl implements IPlatformBankinfoService {
	@Autowired
	private PlatformBankinfoMapper platformBankinfoMapper;

	@Override
	public int save(PlatformBankinfo platformBankinfo) {
		return platformBankinfoMapper.insert(platformBankinfo);
	}

	@Override
	public int update(PlatformBankinfo platformBankinfo) {
		return platformBankinfoMapper.updateByPrimaryKey(platformBankinfo);
	}

	@Override
	public PlatformBankinfo get(Long id) {
		return platformBankinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo queryPage(PlatformBankinfoQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
		List list = platformBankinfoMapper.queryPage(qo);
		return new PageInfo(list);
	}

	@Override
	public void saveOrUpdate(PlatformBankinfo platformBankinfo) {
		//因为是内部操作,所以只是判断id
		if ( platformBankinfo.getId() == null ) {
			this.save(platformBankinfo);
		} else {
			this.update(platformBankinfo);
		}
	}

	@Override
	public List<PlatformBankinfo> selectAll() {
		return platformBankinfoMapper.selectAll();
	}
}

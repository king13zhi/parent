package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.PlatformBankinfo;
import com._163.king13zhi.p2p.business.query.PlatformBankinfoQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by kingdan on 2018/1/27.
 */
public interface IPlatformBankinfoService {
	int save(PlatformBankinfo platformBankinfo);
	int update(PlatformBankinfo platformBankinfo);
	PlatformBankinfo get(Long id);

	PageInfo queryPage(PlatformBankinfoQueryObject qo);

	void saveOrUpdate(PlatformBankinfo platformBankinfo);

	List<PlatformBankinfo> selectAll();
}

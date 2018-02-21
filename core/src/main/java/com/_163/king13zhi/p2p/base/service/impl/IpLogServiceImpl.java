package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.IpLog;
import com._163.king13zhi.p2p.base.mapper.IpLogMapper;
import com._163.king13zhi.p2p.base.query.IplogQueryObject;
import com._163.king13zhi.p2p.base.service.IIpLogService;
import com._163.king13zhi.p2p.base.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingdan on 2018/1/20.
 */
@Service
@Transactional
public class IpLogServiceImpl implements IIpLogService {
	@Autowired
	private IpLogMapper ipLogMapper;

	@Override
	public int save(IpLog ipLog) {
		return ipLogMapper.insert(ipLog);
	}

	@Override
	public PageInfo queryPageHelper(IplogQueryObject qo) {
		/**
		 * PageHelper中的startPage方法,它的作用:
		 * 相当于以前的将start ,  pagesize 参数进行分页limit的参数
		 * 这个方法在这里没有写的话那么拦截器哪里不会自动添加limit到sql语句中去
		 * 这个写了这个之后,mapper.xml中就不用写limit xx,xx;方法,写了会报错.
		 */
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<IpLog> data = ipLogMapper.queryPageDataHelper(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}
}

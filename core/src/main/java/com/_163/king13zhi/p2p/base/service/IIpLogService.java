package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.IpLog;
import com._163.king13zhi.p2p.base.query.IplogQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IIpLogService {
	//一般只要增查全部

	/**
	 * 日志保存方法
	 * @param ipLog
	 * @return 受影响的行数
	 */
	int save(IpLog ipLog);

	/**
	 * 前后台日志分页方法(通过BootStrap插件PageHelper的方法实现)
	 * @param qo
	 * @return PageInfo对象(相当于之前的PageResult对象)
	 */
	PageInfo queryPageHelper(IplogQueryObject qo);
}

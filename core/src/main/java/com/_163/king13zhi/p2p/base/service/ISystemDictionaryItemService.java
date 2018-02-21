package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.Systemdictionaryitem;
import com._163.king13zhi.p2p.base.query.SystemdictionaryitemQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface ISystemDictionaryItemService {

	/**
	 * 数据字典的保存方法
	 * @param systemdictionaryitem
	 * @return 操作成功受影响的行数
	 */
	int save(Systemdictionaryitem systemdictionaryitem);

	/**
	 * 数据字典的更新方法
	 * @param systemdictionaryitem
	 * @return 操作成功受影响的行数
	 */
	int update(Systemdictionaryitem systemdictionaryitem);

	/**
	 * id的查询方法
	 * @param id
	 * @return
	 */
	Systemdictionaryitem get(Long id);

	/**
	 * 数据字典明细通过数据字典sn来进行查询明细
	 * @param id
	 * @param title
	 * @return 方法单个Systemdictionaryitem对象
	 */
	List<Systemdictionaryitem> queryListBySn(String sn);

	/**
	 * 分页相关的操作方法
	 * @param qo
	 * @return
	 */
	PageInfo queryPageHelper(SystemdictionaryitemQueryObject qo);

	/**
	 * 保存及更新方法
	 * @param systemdictionaryitem
	 */
	void saveOrUpdate(Systemdictionaryitem systemdictionaryitem);

}

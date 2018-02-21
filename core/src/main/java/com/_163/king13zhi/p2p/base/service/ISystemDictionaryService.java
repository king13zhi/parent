package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.Systemdictionary;
import com._163.king13zhi.p2p.base.query.SystemdictionaryQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface ISystemDictionaryService {
	/**
	 * 数据字典的保存方法
	 * @param systemdictionary
	 * @return 操作成功受影响的行数
	 */
	int save(Systemdictionary systemdictionary);

	/**
	 * 数据字典的更新方法
	 * @param systemdictionary
	 * @return 操作成功受影响的行数
	 */
	int update(Systemdictionary systemdictionary); 

	/**
	 * 数据字典的单独查询方法
	 * @param id
	 * @param title
	 * @return 方法单个Systemdictionaryitem对象
	 */
	Systemdictionary get(Long id);

	/**
	 * 分页想过操作的方法
	 * @param qo
	 * @return
	 */
	PageInfo queryPageHelper(SystemdictionaryQueryObject qo);

	void saveOrUpdate(Systemdictionary systemdictionary);

	List<Systemdictionary> selectAll();
}

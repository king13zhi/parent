package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.Systemdictionary;
import com._163.king13zhi.p2p.base.query.SystemdictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int insert(Systemdictionary record);
    Systemdictionary selectByPrimaryKey(Long id);
    List<Systemdictionary> selectAll();
    int updateByPrimaryKey(Systemdictionary record);

	List queryPageDataHelper(SystemdictionaryQueryObject qo);
}
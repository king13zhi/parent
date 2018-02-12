package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.Systemdictionaryitem;
import com._163.king13zhi.p2p.base.query.SystemdictionaryitemQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int insert(Systemdictionaryitem record);

    Systemdictionaryitem selectByPrimaryKey(Long id);

    List<Systemdictionaryitem> selectAll();
    int updateByPrimaryKey(Systemdictionaryitem record);

	List queryPageDataHelper(SystemdictionaryitemQueryObject qo);

	List<Systemdictionaryitem> queryListBySn(String sn);
}
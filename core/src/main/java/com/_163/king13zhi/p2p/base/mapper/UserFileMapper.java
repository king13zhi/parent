package com._163.king13zhi.p2p.base.mapper;

import com._163.king13zhi.p2p.base.domain.UserFile;
import com._163.king13zhi.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserFile record);

	List<UserFile> querySelectFileTypeList(Long userId);

	List<UserFile> selectFileTypeByCondition(@Param("userId") Long userId, @Param("isSelectFiltType") boolean isSelectFiltType);

	List queryPage(UserFileQueryObject qo);

	List<UserFile> queryByUserId(@Param("userId") Long userId, @Param("state") int state);
}
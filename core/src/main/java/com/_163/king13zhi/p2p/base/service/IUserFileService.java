package com._163.king13zhi.p2p.base.service;

import com._163.king13zhi.p2p.base.domain.UserFile;
import com._163.king13zhi.p2p.base.query.UserFileQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by kingdan on 2018/1/17.
 */
public interface IUserFileService {
	int save(UserFile userFile);
	int update(UserFile userFile);
	UserFile get(Long id);

	void apply(String imgPath);

	/**
	 * 用户没有选择风控类型的集合数据
	 * @return
	 */
	List<UserFile> querySelectFileTypeList();

	/**
	 * 给上传图片设置风控类型
	 * @param id
	 * @param fileType
	 */
	void selectType(Long[] ids, Long[] fileTypes);

	/**
	 * 查询风控材料集合
	 * @param isSelectFiltType  为true,查询当前用户已经选择风控类型的集合
	 *                          为false,查询当前用户没有选择风控类型的集合
	 * @return
	 */
	List<UserFile> selectFileTypeByCondition(boolean isSelectFiltType);

	/**
	 * 风控材料分页
	 * @param qo
	 * @return
	 */
	PageInfo queryPage(UserFileQueryObject qo);

	/**
	 * 风控材料审核
	 * @param id
	 * @param state
	 * @param score
	 * @param remark
	 */
	void audit(Long id, int state, int score, String remark);

	List<UserFile> queryByUserId(Long userId);
}

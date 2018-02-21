package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Systemdictionaryitem;
import com._163.king13zhi.p2p.base.domain.UserFile;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.mapper.UserFileMapper;
import com._163.king13zhi.p2p.base.query.UserFileQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryItemService;
import com._163.king13zhi.p2p.base.service.IUserFileService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by kingdan on 2018/1/24.
 */
@Service
@Transactional
public class UserFileServiceImpl implements IUserFileService {
	@Autowired
	private UserFileMapper userFileMapper;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;

	@Override
	public int save(UserFile userFile) {
		//userFile.setAuditor();
		return userFileMapper.insert(userFile);
	}

	@Override
	public int update(UserFile userFile) {
		return userFileMapper.updateByPrimaryKey(userFile);
	}

	@Override
	public UserFile get(Long id) {
		return userFileMapper.selectByPrimaryKey(id);
	}

	@Override
	public void apply(String imgPath) {
		UserFile uf = new UserFile();
		uf.setImage(imgPath);
		uf.setApplier(UserContext.getCurrent());
		uf.setApplyTime(new Date());
		uf.setState(UserFile.STATE_NORMAL);
		this.save(uf);
	}

	@Override
	public List<UserFile> querySelectFileTypeList() {
		return userFileMapper.querySelectFileTypeList(UserContext.getCurrent().getId());
	}

	@Override
	public void selectType(Long[] ids, Long[] fileTypes) {
		//判断非空和两个数组数据一一对应
		if ( ids != null && fileTypes != null && ids.length == fileTypes.length ) {
			UserFile uf;
			Systemdictionaryitem fileType;
			for (int i = 0; i < ids.length; i++) {
				uf = this.get(ids[i]);
				//判断该条分控记录是否属于当前登录用户
				if ( UserContext.getCurrent().getId().equals(uf.getApplier().getId()) ) {
					//只是设置id,关联额外查询,会自动触发额外查询.
					fileType = new Systemdictionaryitem();
					fileType.setId(fileTypes[i]);
					//fileType = systemDictionaryItemService.get(fileTypes[i]); //也可以直接把这个对象直接查询出来.
					uf.setFileType(fileType);
					this.update(uf);
				}
			}
		}
	}

	@Override
	public List<UserFile> selectFileTypeByCondition(boolean isSelectFiltType) {
		return userFileMapper.selectFileTypeByCondition(UserContext.getCurrent().getId(), isSelectFiltType);
	}

	@Override
	public PageInfo queryPage(UserFileQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List data = userFileMapper.queryPage(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}

	@Override
	public void audit(Long id, int state, int score, String remark) {
		//1.根据id获取风控材料对象,处于待审核
		UserFile userFile = this.get(id);
		if ( userFile != null && userFile.getState() == UserFile.STATE_NORMAL ) {
			//2.设置上审核人,审核时间,审核备注
			userFile.setAuditor(UserContext.getCurrent());
			userFile.setAuditTime(new Date());
			userFile.setRemark(remark);
			if ( state == UserFile.STATE_PASS ) {
				//3.审核通过  设置状态
				userFile.setState(UserFile.STATE_PASS);
				userFile.setScore(score);
				//找到申请人的userinfo对象,score字段添加相对应的分数
				Userinfo applierUserInfo = userinfoService.get(userFile.getApplier().getId());
				applierUserInfo.setScore(applierUserInfo.getScore() + score);
				userinfoService.update(applierUserInfo);
			} else {
				//4.审核失败  设置状态
				userFile.setState(UserFile.STATE_REJECT);
			}
			this.update(userFile);
		}
	}

	@Override
	public List<UserFile> queryByUserId(Long userId) {
		return userFileMapper.queryByUserId(userId,UserFile.STATE_PASS);
	}
}

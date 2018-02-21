package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.domain.VedioAuth;
import com._163.king13zhi.p2p.base.mapper.VedioAuthMapper;
import com._163.king13zhi.p2p.base.query.VideoAuthQueryObject;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.service.IVedioAuthService;
import com._163.king13zhi.p2p.base.util.BitStatesUtils;
import com._163.king13zhi.p2p.base.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by kingdan on 2018/1/18.
 */
@Service
@Transactional
public class VedioAuthServiceImpl implements IVedioAuthService {
	@Autowired
	private VedioAuthMapper vedioAuthMapper;
	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public int save(VedioAuth vedioAuth) {
		return vedioAuthMapper.insert(vedioAuth);
	}

	@Override
	public int update(VedioAuth vedioAuth) {
		return vedioAuthMapper.updateByPrimaryKey(vedioAuth);
	}

	@Override
	public VedioAuth get(Long id) {
		return vedioAuthMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo queryPageHelper(VideoAuthQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List data = vedioAuthMapper.queryPageDataHelper(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}

	@Override
	public void audit(Long applierId, int state, String remark) {
		// 判断用户没有视频认证
		Userinfo user = this.userinfoService.get(applierId);
		if ( user != null && !user.getIsVedioAuth() ) {
			// 添加一个视频认证对象,设置相关属性
			VedioAuth va = new VedioAuth();
			Logininfo applier = new Logininfo();
			applier.setId(applierId);
			va.setApplier(applier);
			va.setApplyTime(new Date());
			va.setAuditor(UserContext.getCurrent());
			va.setAuditTime(new Date());
			va.setRemark(remark);
			va.setState(state);
			this.vedioAuthMapper.insert(va);
			if ( state == VedioAuth.STATE_PASS ) {
				// 如果状态审核通过,修改用户状态码
				user.addState(BitStatesUtils.OP_VEDIO_AUTH);
				this.userinfoService.update(user);
			}
		}
	}
}

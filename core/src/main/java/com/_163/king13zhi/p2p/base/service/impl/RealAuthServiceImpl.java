package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.RealAuth;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.mapper.RealAuthMapper;
import com._163.king13zhi.p2p.base.query.RealAuthQueryObject;
import com._163.king13zhi.p2p.base.service.IRealAuthService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
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
 * Created by kingdan on 2018/1/23.
 */
@Service@Transactional
public class RealAuthServiceImpl implements IRealAuthService {
	@Autowired
	private RealAuthMapper realAuthMapper;
	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public int save(RealAuth realAuth) {
		return realAuthMapper.insert(realAuth);
	}

	@Override
	public int update(RealAuth realAuth) {
		return realAuthMapper.updateByPrimaryKey(realAuth);
	}

	@Override
	public RealAuth get(Long id) {
		return realAuthMapper.selectByPrimaryKey(id);
	}

	@Override
	public void realAuthSave(RealAuth realAuth) {
		//把实名认证信息存入到数据库中
		RealAuth re = new RealAuth();
		re.setAddress(realAuth.getAddress());
		re.setApplier(UserContext.getCurrent());
		re.setApplyTime(new Date());
		re.setBornDate(realAuth.getBornDate());
		re.setIdNumber(realAuth.getIdNumber());
		re.setImage1(realAuth.getImage1());
		re.setImage2(realAuth.getImage2());
		re.setRealName(realAuth.getRealName());
		re.setSex(realAuth.getSex());
		re.setState(RealAuth.STATE_NORMAL);
		this.save(re);
		//把实名认证对象设置到userinfo当中的realauthid字段.
		Userinfo userinfo = userinfoService.getCurrent();
		userinfo.setRealAuthId(re.getId());
		userinfoService.update(userinfo);
	}

	@Override
	public void audit(Long id, int state, String remark) {
		//1.根据id查询实名认证审核对象,判断是否为null,判断是否已经审核.
		RealAuth realAuth = this.get(id);
		if (realAuth != null && realAuth.getState() == RealAuth.STATE_NORMAL) {
			//2.设置审核人,审核时间,审核备注
			realAuth.setAuditor(UserContext.getCurrent());
			realAuth.setAuditTime(new Date());
			realAuth.setRemark(remark);
			//获取到申请人的userinfo
			Userinfo applierUserinfo = userinfoService.get(realAuth.getApplier().getId());
			//3.审核公国
			if (state == RealAuth.STATE_PASS) {
				//状态修改审核通过
				realAuth.setState(RealAuth.STATE_PASS);
				//给申请人的suerinfo添加实名认证的状态码
				applierUserinfo.addState(BitStatesUtils.OP_REAL_AUTH);
				//设置申请人的userinfo的realName和idNumber
				applierUserinfo.setRealName(realAuth.getRealName());
				applierUserinfo.setIdNumber(realAuth.getIdNumber());
			} else {
				//4.审核拒绝
				realAuth.setState(RealAuth.STATE_REJECT);
				//状态修改成审核拒绝
				//把userinfo中的realAuthId设置为null
				applierUserinfo.setRealAuthId(null);
			}
			this.update(realAuth);
			userinfoService.update(applierUserinfo);
		}
	}

	@Override
	public PageInfo queryPage(RealAuthQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
		List data = realAuthMapper.queryPage(qo);
		//通过查询数据后封装到PageInfo中
		return new PageInfo(data);
	}
}

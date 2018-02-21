package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.MailVerify;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.mapper.UserinfoMapper;
import com._163.king13zhi.p2p.base.service.IMailVerifyService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.service.IVerifyCodeService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.BitStatesUtils;
import com._163.king13zhi.p2p.base.util.DateUtil;
import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/18.
 */
@Service@Transactional
public class UserinfoServiceImpl implements IUserinfoService {
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private IVerifyCodeService verifyCodeService;
	@Autowired
	private IMailVerifyService mailVerifyService;

	@Override
	public int save(Userinfo userinfo) {
		return userinfoMapper.insert(userinfo);
	}

	@Override
	public int update(Userinfo userinfo) {
		int count = userinfoMapper.updateByPrimaryKey(userinfo);
		if (count == 0) {
			throw new RuntimeException("乐观锁异常,userinfoId" + userinfo.getId());
		}
		return count;
	}

	@Override
	public Userinfo get(Long id) {
		return userinfoMapper.selectByPrimaryKey(id);
	}

	//获取当前登录用户的个人中心
	@Override
	public Userinfo getCurrent() {
		return this.get(UserContext.getCurrent().getId());
	}

	@Override
	public void bindPhone(String phoneNumber, String verifyCode) {
		//1.验证验证码是否有效,手机号码是否之前发送短信的手机号码,验证码在有效时间之类
		boolean isVaild = verifyCodeService.validate(phoneNumber, verifyCode);
		if (!isVaild) {
			throw new RuntimeException("验证码有误,请重新发送");
		}
		//2.判断用户是否已经绑定了手机号码
		Userinfo userinfo = this.getCurrent();
		if (userinfo.getHasBindPhone()) {
			throw new RuntimeException("你已经绑定手机号码了,请不要重复的绑定");
		}
		//3.给用户的userinfo添加手机认证的状态码
		userinfo.addState(BitStatesUtils.OP_BIND_PHONE);
		userinfo.setPhoneNumber(phoneNumber);
		this.update(userinfo);
	}

	@Override
	public void bindEmail(String key) {
		//从数据库中根据uuid查询记录
		MailVerify mailVerify = mailVerifyService.get(key);
		if (mailVerify == null) {
			throw new RuntimeException("验证邮箱地址有误,请重新发送!");
		}
		//判断邮件是否在有效时间内.
		if (DateUtil.getBetweenTime(mailVerify.getSendTime(), new Date()) > BidConst.EMAIL_VAILD_TIME * 24 * 60 * 60) {
			throw new RuntimeException("验证邮件已经失效,请重新发送!");
		}
		//判断用户是否已经绑定邮箱了
		Userinfo userinfo = this.get(mailVerify.getUserId()); //这里不能从session中获取,邮箱验证会在多个客户端,session失效.
		if (userinfo.getHasBindEmail()) {
			throw new RuntimeException("你已经绑定邮箱了,请不要重复绑定!");
		}
		//给用户添加邮箱验证码
		userinfo.addState(BitStatesUtils.OP_BIND_EMAIL);
		//给用户的userinfo中email设置值
		userinfo.setEmail(mailVerify.getEmail());
		//更新userinfo对象
		this.update(userinfo);
	}

	@Override
	public void basicInfoSave(Userinfo userinfo) {
		//页面传递过来的值(关键的)最好是new出来然后进行set注入值,防止恶意数据注入.
		Userinfo currentUserinfo = this.getCurrent();
		currentUserinfo.setEducationBackground(userinfo.getEducationBackground());
		currentUserinfo.setHouseCondition(userinfo.getHouseCondition());
		currentUserinfo.setKidCount(userinfo.getKidCount());
		currentUserinfo.setMarriage(userinfo.getMarriage());
		currentUserinfo.setIncomeGrade(userinfo.getIncomeGrade());
		//如果之前没有进行个人信息认证,那么就改变认证状态
		if (!currentUserinfo.getIsBasicInfo()) {
			currentUserinfo.addState(BitStatesUtils.OP_BASIC_INFO);
		}
		//更新操作
		this.update(currentUserinfo);
	}


}
























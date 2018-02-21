package com._163.king13zhi.p2p.base.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.domain.IpLog;
import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.mapper.LogininfoMapper;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.service.IIpLogService;
import com._163.king13zhi.p2p.base.service.ILogininfoService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.MD5;
import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/17.
 */
@Service@Transactional
public class LogininfoServiceImpl implements ILogininfoService {
	@Autowired
	private LogininfoMapper logininfoMapper;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IIpLogService ipLogService;

	@Override
	public Logininfo register(String username, String password) {
		//注册的流程
		//1.先在数据库中查询该用户名是否已经存在
		int count = logininfoMapper.queryCountByUsername(username);
		//2.如果存在则提示用户改用户名已经注册
		if (count > 0) {
			throw new RuntimeException("该用户名已经注册");
		}
		//3.如果不存在则正常保存入库
		Logininfo logininfo = new Logininfo();
		logininfo.setUsername(username);
		logininfo.setPassword(MD5.encode(password));
		logininfo.setState(Logininfo.STATE_NORMAL);
		logininfo.setUserType(Logininfo.USERTYPE_USER);
		//如果不存在则直接保存入库
		logininfoMapper.insert(logininfo);
		//--------------------------------------
		//创建登录之后则生成Account和userinfo对象
		Account account = new Account();
		account.setId(logininfo.getId());
		accountService.save(account);
		//-------------------------------------
		Userinfo userinfo = new Userinfo();
		userinfo.setId(logininfo.getId());
		userinfoService.save(userinfo);
		//--------------------------------------
		return logininfo;
	}

	@Override
	public boolean queryUsername(String username) {
		return logininfoMapper.queryCountByUsername(username)<=0;
	}

	@Override
	public Logininfo login(String username, String password,int userType) {
		//1.从数据库中通过用户名和密码查询是否有该用户
		Logininfo logininfo = logininfoMapper.selectLogininfoByUsrenameAndPassword(username,MD5.encode(password),userType);

		//----------------------------------------
		IpLog iplog = new IpLog();
		//获取登录者的ip
		iplog.setIp(UserContext.getIp());
		//获取登录时间
		iplog.setLoginTime(new Date());
		//获取当前登录的用户名(不管是否登录成功)
		iplog.setUsername(username);
		//设置日志状态
		iplog.setUserType(userType);
		//2.如果存在,则将该用户存入到session中
		//----------------------------------------
		if (logininfo != null) {
			//如果登录对象不为空,那么就是登录成功.
			UserContext.setCurrent(logininfo);
			//获取当前登录用户
			iplog.setState(IpLog.LOGIN_FAILED);
			//将当前用户的信息保存到数据库中
			ipLogService.save(iplog);
		} else {
		//3.如果没有则提示用户
			//如果登录对象为空那么就是登录失败
			iplog.setState(IpLog.LOGIN_SUCCESS);
			//将当前用户的信息保存到数据库中
			ipLogService.save(iplog);
		}
		//-----------------------------------------
		return logininfo;
	}

	@Override
	public void initAdmin() {
		//根据类型查找数据库中是否有管理员
		int count = logininfoMapper.queryCountByUserType(Logininfo.USERTYPE_MANAGER);
		//如果没有,创建第一个管理员
		if (count <= 0) {
			Logininfo logininfo = new Logininfo();
			logininfo.setState(Logininfo.STATE_NORMAL);
			logininfo.setUserType(Logininfo.USERTYPE_MANAGER);
			//将这两个参数变量名抽取到BidConst类中
			logininfo.setUsername(BidConst.DEFAULT_ADMIN_NAME);
			logininfo.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD));
			logininfoMapper.insert(logininfo);
		}
	}

	@Override
	public List<Map<String, Object>> queryAutoComplete(String keyword) {
		return this.logininfoMapper.autocomplate(keyword);
	}
}

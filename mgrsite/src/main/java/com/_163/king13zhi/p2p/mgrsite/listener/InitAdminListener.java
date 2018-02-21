package com._163.king13zhi.p2p.mgrsite.listener;

import com._163.king13zhi.p2p.base.service.ILogininfoService;
import com._163.king13zhi.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by kingdan on 2018/1/20.
 */
@Component  //这个类受到spring进行管理
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> { //容器启动时受到监听
	@Autowired
	private ILogininfoService logininfoService;
	@Autowired
	private ISystemAccountService systemAccountService;

	/**
	 * 处理应用程序的事件
	 * @param event 当Application被初始化时触发监听事件
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		systemAccountService.initSystemAccount();
		logininfoService.initAdmin();
	}
}

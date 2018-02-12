package com._163.king13zhi.p2p.website.interceptor;

import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.website.annotion.RequireLogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kingdan on 2018/1/21.
 */

/**
 * web一般分为一般页面和个人中心,个人中心需要登录才能访问的.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//1.获取请求的方法
		HandlerMethod hm = (HandlerMethod) handler;
		RequireLogin requireLogin = hm.getMethodAnnotation(RequireLogin.class);
		//判断方法上是否有贴注解
		if (requireLogin != null) {
			//该方法需要登录才能访问
			if (UserContext.getCurrent() == null) {
				response.sendRedirect("/login.html");
				return false;
			}
		}
		return true;
	}
}


































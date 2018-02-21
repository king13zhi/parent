package com._163.king13zhi.p2p.mgrsite.interceptor;

import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kingdan on 2018/1/21.
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//判断方法上是否有贴注解
		//该方法需要登录才能访问
		if (UserContext.getCurrent() == null) {
			response.sendRedirect("/login.html");
			return false;
		}
		return true;
	}
}


































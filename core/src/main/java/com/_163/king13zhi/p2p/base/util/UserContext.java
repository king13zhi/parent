/*
package com._163.king13zhi.p2p.base.util;

import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.vo.VerifyCodeVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * Created by kingdan on 2018/1/18.
 *//*


//当前登录用户session工具类
public class UserContext {
	//当前用户信息
	private static final String USER_IN_SESSION = "logininfo";
	private static final String VERIFYCODE_IN_SESSION = "verifyCode";

	//获取当前线程上的request
	private static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	//将当前用户保存到session中(前台和后台在不同的场景获取不同的对象)
	public static void setCurrent(Logininfo logininfo) {
		getRequest().getSession().setAttribute(USER_IN_SESSION,logininfo);
	}

	//从当前线程中获取当前登录的用户信息
	public static Logininfo getCurrent() {
		return (Logininfo) getRequest().getSession().getAttribute(USER_IN_SESSION);
	}

	//获取访问者的ip
	public static String getIp() {
		return getRequest().getRemoteAddr();
	}

	//将email认证相关的信息设置到session中
	public static void setVerifyCodeVo(VerifyCodeVo vo) {
		getRequest().getSession().setAttribute(VERIFYCODE_IN_SESSION,vo);
	}

	//从session中获取认证信息
	public static VerifyCodeVo getVerifyCodeVo(){
		return (VerifyCodeVo) getRequest().getSession().getAttribute(VERIFYCODE_IN_SESSION);
	}
}
*/

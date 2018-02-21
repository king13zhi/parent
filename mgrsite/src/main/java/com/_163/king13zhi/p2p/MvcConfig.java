package com._163.king13zhi.p2p;

import com._163.king13zhi.p2p.mgrsite.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kingdan on 2018/1/21.
 */

/**
 * 后台管理配置拦截器
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * 拦截那些资源
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//除了登录其余都进行拦截(因为后台管理都需要登录才可以,没有根据是否有注解进行判断)
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/*").excludePathPatterns("/managerLogin");//除了登录
	}

	/**
	 * 注入自定义拦截器
	 * @return
	 */
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
}





















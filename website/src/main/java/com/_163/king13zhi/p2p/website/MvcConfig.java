package com._163.king13zhi.p2p.website;

import com._163.king13zhi.p2p.website.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kingdan on 2018/1/21.
 */

/**
 * 配置拦截器
 */

/**
 * xml配置类,只是配置的话,那么直接用configuration.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * 拦截那些资源
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/*");
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





















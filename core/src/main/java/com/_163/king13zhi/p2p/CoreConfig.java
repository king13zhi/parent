package com._163.king13zhi.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration  //因为不被访问所以用Configuration即可
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
/*@PropertySources({
		*//*@PropertySource("classpath:application-core.properties"),*//*
		@PropertySource("classpath:email.properties"),
		@PropertySource("classpath:msg.properties") //applciation.properties只可以唯一,所以如果使用别的名字可以这样改
		})*/
@MapperScan({"com._163.king13zhi.p2p.base.mapper","com._163.king13zhi.p2p.business.mapper"})//扫描Mapper接口包
public class CoreConfig {
	//解析${}表达式,static 优先于对象创建
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreConfig.class, args);
	}
}

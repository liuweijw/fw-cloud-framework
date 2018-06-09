package com.github.liuweijw.business.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 后台管理系统API
 * 
 * @author liuweijw
 */
// @EnableAsync // 开始对异步任务的支持，并在相应的方法中使用@Async注解来声明一个异步任务
// @EnableScheduling
@EnableHystrix
@EnableFeignClients
@SpringCloudApplication
// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.github.liuweijw.business.admin", "com.github.liuweijw.core",
		"com.github.liuweijw.business.commons.web", "com.github.liuweijw.system.api" })
public class FwAdminApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwAdminApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwAdminApplication.class, args);
	}

}

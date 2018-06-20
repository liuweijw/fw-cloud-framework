package com.github.liuweijw.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Auth Server 中心
 * 
 * @author liuweijw
 */
// 认证中心，以及获取用户信息
// @EnableResourceServer
@EnableFeignClients
@SpringCloudApplication
// modelA的java包结构是：com.github.liuweijw.system.auth.xxx
// modelB的java包结构是：com.github.liuweijw.system.api.xxx
// modelB将来要发布成一个jar包，modelA中会依赖modelB的jar包。在测试的时候发现，在modelA中启动时无法扫描到modelB中声明的那些component
// 固在此调整入口FwAuthApplication路径为com.github.liuweijw.system
@ComponentScan(basePackages = { "com.github.liuweijw.system", "com.github.liuweijw.core" })
public class FwAuthApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwAuthApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwAuthApplication.class, args);
	}

}

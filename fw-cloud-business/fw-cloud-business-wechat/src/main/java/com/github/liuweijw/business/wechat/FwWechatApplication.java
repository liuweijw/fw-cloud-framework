package com.github.liuweijw.business.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 微信公众号服务
 * 
 * @author liuweijw
 */
@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.github.liuweijw.business.wechat", "com.github.liuweijw.core" })
public class FwWechatApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwWechatApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwWechatApplication.class, args);
	}

}

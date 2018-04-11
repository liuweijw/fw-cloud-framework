package com.github.liuweijw.business.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 支付平台服务API
 * 
 * @author liuweijw
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.github.liuweijw.business.pay", "com.github.liuweijw.core",
		"com.github.liuweijw.business.commons.web" })
public class FwPayApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwPayApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwPayApplication.class, args);
	}

}

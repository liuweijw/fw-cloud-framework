package com.github.liuweijw.system.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server 配置中心
 * 
 * @author liuweijw
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class FwConfigApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwConfigApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwConfigApplication.class, args);
	}

}

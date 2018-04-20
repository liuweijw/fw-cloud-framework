package com.github.liuweijw.system.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server 中心
 * 
 * @author liuweijw
 */
@EnableEurekaServer
@SpringBootApplication
public class FwEurekaApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwEurekaApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwEurekaApplication.class, args);
	}

}

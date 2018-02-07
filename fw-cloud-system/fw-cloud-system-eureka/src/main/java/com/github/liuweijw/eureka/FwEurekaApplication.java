package com.github.liuweijw.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * Eureka Server 中心
 * 
 * @author liuweijw
 * 
 */
@EnableEurekaServer
@SpringBootApplication
public class FwEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwEurekaApplication.class, args);
	}
	
}

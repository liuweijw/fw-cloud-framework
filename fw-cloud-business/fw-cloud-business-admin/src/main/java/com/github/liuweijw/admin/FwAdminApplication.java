package com.github.liuweijw.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 后台管理系统API
 * 
 * @author liuweijw
 * 
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.github.liuweijw.admin", "com.github.liuweijw.core"})
public class FwAdminApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(FwAdminApplication.class, args);
    }
    
}
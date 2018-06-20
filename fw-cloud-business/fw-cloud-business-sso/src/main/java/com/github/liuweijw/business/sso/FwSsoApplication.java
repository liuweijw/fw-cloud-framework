package com.github.liuweijw.business.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;

/**
 * sso demo
 *
 * @author liuweijw
 */
@EnableOAuth2Sso
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@ComponentScan(basePackages = { "com.github.liuweijw.business.sso" })
public class FwSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwSsoApplication.class, args);
	}

}

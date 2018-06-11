package com.github.liuweijw.system.auth.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuweijw
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "fw.auth")
public class AuthServerConfiguration {

	/**
	 * 客户端id
	 */
	private String	clientId;

	/**
	 * 客户端密码
	 */
	private String	clientSecret;

	/**
	 * scope
	 */
	private String	scope;

	/**
	 * RSA private 密匙
	 */
	private String	privateKey;

	/**
	 * RSA public 公匙
	 */
	private String	publicKey;

}

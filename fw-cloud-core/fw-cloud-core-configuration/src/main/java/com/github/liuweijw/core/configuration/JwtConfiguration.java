package com.github.liuweijw.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fw.jwt")
public class JwtConfiguration {

	/**
	 * JWT key
	 */
	private String	jwtkey;

	public String getJwtkey() {
		return jwtkey;
	}

	public void setJwtkey(String jwtkey) {
		this.jwtkey = jwtkey;
	}

}

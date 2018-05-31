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

	/**
	 * JWT TOKEN PREFIX
	 */
	private String	prefix;

	/**
	 * JWT AUTH REQUEST URL
	 */
	private String	jwtAuthUrl;

	public String getJwtkey() {
		return jwtkey;
	}

	public void setJwtkey(String jwtkey) {
		this.jwtkey = jwtkey;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getJwtAuthUrl() {
		return jwtAuthUrl;
	}

	public void setJwtAuthUrl(String jwtAuthUrl) {
		this.jwtAuthUrl = jwtAuthUrl;
	}

}

package com.github.liuweijw.business.wechat.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "business.config.wechat.mp")
public class WechatMpProperties {
	/**
	 * 设置微信公众号的appid
	 */
	private String	appId;

	/**
	 * 设置微信公众号的app secret
	 */
	private String	secret;

	/**
	 * 设置微信公众号的token
	 */
	private String	token;

	/**
	 * 设置微信公众号的EncodingAESKey
	 */
	private String	aesKey;

	/**
	 * 权限URL
	 */
	private String	authUrl;

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return this.aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

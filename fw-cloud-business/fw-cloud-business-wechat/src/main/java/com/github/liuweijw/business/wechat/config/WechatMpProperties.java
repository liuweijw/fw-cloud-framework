package com.github.liuweijw.business.wechat.config;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信公众号相关配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

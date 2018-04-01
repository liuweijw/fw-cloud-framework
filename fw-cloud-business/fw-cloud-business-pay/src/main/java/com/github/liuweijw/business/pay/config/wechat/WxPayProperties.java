package com.github.liuweijw.business.pay.config.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付相关配置
 * 
 * @author liuweijw
 *
 */
@Configuration
@ConfigurationProperties(prefix = "business.config.wxpay")
public class WxPayProperties {

	private String certRootPath;

	private String notifyUrl;

	public String getCertRootPath() {
		return certRootPath;
	}

	public void setCertRootPath(String certRootPath) {
		this.certRootPath = certRootPath;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}

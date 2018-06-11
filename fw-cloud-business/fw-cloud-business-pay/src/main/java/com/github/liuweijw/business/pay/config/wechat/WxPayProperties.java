package com.github.liuweijw.business.pay.config.wechat;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付相关配置
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "business.config.wxpay")
public class WxPayProperties {

	private String	certRootPath;

	private String	notifyUrl;

}

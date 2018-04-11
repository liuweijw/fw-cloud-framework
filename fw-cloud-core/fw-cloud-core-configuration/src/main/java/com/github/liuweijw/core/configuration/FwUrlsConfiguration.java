package com.github.liuweijw.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 所有需要过滤URL的配置，urls.collects 模式
 * 
 * @author liuweijw
 */
@Configuration
@ConditionalOnExpression("!'${urls}'.isEmpty()")
@ConfigurationProperties(prefix = "urls")
public class FwUrlsConfiguration {

	private List<String>	collects	= new ArrayList<>();

	public List<String> getCollects() {
		return collects;
	}

	public void setCollects(List<String> collects) {
		this.collects = collects;
	}

}

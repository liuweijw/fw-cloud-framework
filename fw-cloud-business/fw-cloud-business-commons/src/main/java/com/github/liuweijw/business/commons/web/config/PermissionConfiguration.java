package com.github.liuweijw.business.commons.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * business 相关模块的配置
 * 
 * @author liuweijw
 */
@Configuration
@ConfigurationProperties(prefix = "business.permission")
public class PermissionConfiguration {

	/**
	 * Flag to enable the @PrePermissions. Default true.
	 */
	private boolean	enabled	= true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}

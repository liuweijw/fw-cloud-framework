package com.github.liuweijw.system.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import com.github.liuweijw.core.configuration.FwUrlsConfiguration;
import com.github.liuweijw.system.auth.component.ajax.AjaxSecurityConfigurer;

/**
 * @author liuweijw 认证服务器开放接口配置
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 2)
@Configuration
// @EnableResourceServer
@EnableWebSecurity
public class FwResourceServerConfiguration extends WebSecurityConfigurerAdapter {// ResourceServerConfigurerAdapter {

	@Autowired
	private FwUrlsConfiguration		fwUrlsConfiguration;

	@Autowired
	private AjaxSecurityConfigurer	ajaxSecurityConfigurer;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.formLogin()
				// 可以通过授权登录进行访问
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/signin")
				.and()
				.authorizeRequests();

		for (String url : fwUrlsConfiguration.getCollects()) {
			registry.antMatchers(url)
					.permitAll();
		}

		registry.anyRequest()
				.authenticated()
				.and()
				.csrf()
				.disable();
		http.apply(ajaxSecurityConfigurer);
	}

}

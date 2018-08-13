package com.github.liuweijw.system.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.liuweijw.core.configuration.FwUrlsConfiguration;
import com.github.liuweijw.system.gateway.filter.ValidateCodeFilter;
import com.github.liuweijw.system.gateway.handler.AccessDeniedHandler;

/**
 * @author luozhonghua
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private FwUrlsConfiguration					urlsConfiguration;

	@Autowired
	private OAuth2WebSecurityExpressionHandler	expressionHandler;

	@Autowired
	private AccessDeniedHandler					accessDeniedHandler;

	@Autowired
	private ValidateCodeFilter					validateCodeFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
		// 允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
		http.headers().frameOptions().disable();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		for (String url : urlsConfiguration.getCollects()) {
			registry.antMatchers(url).permitAll();
		}
		registry.anyRequest().access("@permissionService.hasPermission(request,authentication)");
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.expressionHandler(expressionHandler);
		resources.accessDeniedHandler(accessDeniedHandler);
	}

	/**
	 * 配置解决 spring-security-oauth问题 https://github.com/spring-projects/spring-security-oauth/issues/730
	 *
	 * @param applicationContext
	 *            ApplicationContext
	 * @return OAuth2WebSecurityExpressionHandler
	 */
	@Bean
	public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(
			ApplicationContext applicationContext) {
		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setApplicationContext(applicationContext);
		return expressionHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

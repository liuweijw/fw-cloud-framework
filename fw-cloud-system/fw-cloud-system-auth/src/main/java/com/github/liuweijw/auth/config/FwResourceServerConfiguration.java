package com.github.liuweijw.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.github.liuweijw.auth.component.ajax.AjaxSecurityConfigurer;
import com.github.liuweijw.core.configuration.FwUrlsConfiguration;

/**
 * @author liuweijw
 * 
 * 认证服务器开放接口配置
 */
@Configuration
@EnableResourceServer
public class FwResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
    @Autowired
    private FwUrlsConfiguration fwUrlsConfiguration;
    
    @Autowired
    private AjaxSecurityConfigurer ajaxSecurityConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        
        for (String url : fwUrlsConfiguration.getCollects()) {
            registry.antMatchers(url).permitAll();
        }
        
        registry.anyRequest().authenticated()
                .and()
                .csrf().disable();
        http.apply(ajaxSecurityConfigurer);
    }

}
package com.github.liuweijw.core.commons.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.liuweijw.core.commons.web.resolver.TokenArgumentResolver;

/**
 * @author liuweijw
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TokenArgumentResolver(cacheManager));
    }
}

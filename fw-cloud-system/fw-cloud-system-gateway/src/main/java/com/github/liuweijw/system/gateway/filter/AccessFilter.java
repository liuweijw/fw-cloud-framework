package com.github.liuweijw.system.gateway.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiaoleilu.hutool.collection.CollectionUtil;

/**
 * 在RateLimitPreFilter 之前执行，否则会出现空指针问题
 * 
 * @author liuweijw
 */
@Component
public class AccessFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FORM_BODY_WRAPPER_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.set("startTime", System.currentTimeMillis());

		// 传递 { @link SecurityConstant.ROLE_HEADER } 头部角色权限到下游请求
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null
				&& !"anonymousUser".equals(authentication.getPrincipal().toString())) {
			ctx.addZuulRequestHeader(SecurityConstant.USER_HEADER, authentication.getName());
			ctx.addZuulRequestHeader(
					SecurityConstant.ROLE_HEADER, CollectionUtil.join(
							authentication.getAuthorities(), ","));
		}
		return null;
	}
}

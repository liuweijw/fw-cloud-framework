package com.github.liuweijw.system.gateway.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.system.gateway.service.LogService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 网关统一异常处理
 * 
 * @author liuweijw
 */
@Component
public class ErrorHandlerFilter extends ZuulFilter {

	@Autowired
	private LogService logService;

	@Override
	public String filterType() {
		return ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return SEND_RESPONSE_FILTER_ORDER + 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		return requestContext.getThrowable() != null;
	}

	@Override
	public Object run() {
		// 发送 mq 记录日志
		RequestContext requestContext = RequestContext.getCurrentContext();
		logService.send(requestContext);
		return null;
	}

}

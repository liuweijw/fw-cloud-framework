package com.github.liuweijw.system.gateway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * 往消息通道发消息
 * 
 * @author liuweijw
 */
public interface LogService {

	/**
	 * 往消息通道发消息
	 */
	void send(RequestContext requestContext);
}

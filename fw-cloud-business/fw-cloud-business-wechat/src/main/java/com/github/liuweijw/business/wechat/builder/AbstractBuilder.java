package com.github.liuweijw.business.wechat.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 构建消息
 * 
 * @author liuweijw
 */
public abstract class AbstractBuilder {

	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	public abstract WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
			WxMpService service);
}

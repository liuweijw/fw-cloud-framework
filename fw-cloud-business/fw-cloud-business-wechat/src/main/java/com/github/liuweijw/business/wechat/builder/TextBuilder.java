package com.github.liuweijw.business.wechat.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * 文本消息
 * 
 * @author luozhonghua
 */
public class TextBuilder extends AbstractBuilder {

	@Override
	public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {
		WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(content).fromUser(
				wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
		return m;
	}

}

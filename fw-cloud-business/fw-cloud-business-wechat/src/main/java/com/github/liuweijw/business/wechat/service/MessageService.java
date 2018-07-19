package com.github.liuweijw.business.wechat.service;

import me.chanjar.weixin.common.error.WxErrorException;

import com.github.liuweijw.commons.pay.beans.MsgBean;
import com.github.liuweijw.commons.pay.enums.WxTemplateEnum;

public interface MessageService {

	/**
	 * 发送微信模板消息通知
	 * 
	 * @param wxTemplateEnum
	 */
	boolean sendWeixinTemplateMessage(WxTemplateEnum wxTemplateEnum, MsgBean msgBean)
			throws WxErrorException;

}

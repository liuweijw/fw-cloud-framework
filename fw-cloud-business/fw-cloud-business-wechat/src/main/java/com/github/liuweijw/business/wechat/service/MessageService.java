package com.github.liuweijw.business.wechat.service;

import com.github.liuweijw.commons.pay.beans.MsgBean;
import com.github.liuweijw.commons.pay.enums.WxTemplateEnum;

import me.chanjar.weixin.common.error.WxErrorException;

public interface MessageService {

	/**
	 * 发送微信模板消息通知
	 * 
	 * @param wxTemplateEnum
	 */
	@Deprecated
	boolean sendWeixinTemplateMessage(WxTemplateEnum wxTemplateEnum, MsgBean msgBean)
			throws WxErrorException;

	/**
	 * 发送微信模板消息通知
	 * 
	 * @param msgBean
	 *            消息内容
	 */
	boolean sendWeixinCurrencyTemplateMessage(MsgBean msgBean) throws WxErrorException;

}

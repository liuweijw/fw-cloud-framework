package com.github.liuweijw.business.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.liuweijw.business.wechat.domain.WechatInfo;
import com.github.liuweijw.business.wechat.service.MessageService;
import com.github.liuweijw.business.wechat.service.WechatInfoService;
import com.github.liuweijw.commons.pay.beans.HttpResult;
import com.github.liuweijw.commons.pay.beans.MsgBean;
import com.github.liuweijw.commons.pay.enums.WxTemplateEnum;
import com.github.liuweijw.commons.utils.Crypt;
import com.github.liuweijw.commons.utils.StringHelper;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信公众号消息通过openId发送
 * 
 * @author liuweijw
 */
@Slf4j
@Controller
@RequestMapping("/wechat/message")
public class WxMessageController {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private WechatInfoService	wechatInfoService;

	@ResponseBody
	@Deprecated
	@RequestMapping(value = "/send/{wechatId}")
	public HttpResult sendWeixinTemplateMessage(@PathVariable("wechatId") String wechatId,
			@RequestParam("message") String message) {
		if (StringHelper.isBlank(message)) return new HttpResult().failure("发送消息内容不能为空");

		WechatInfo wechatInfo = wechatInfoService.findByWechatId(wechatId);
		if (null == wechatInfo)
			return new HttpResult().failure("公众号wechatId[" + wechatId + "]不存在");

		log.info(
				"公众号消息发送：|wechatId[{}]|reqKey[{}]|message[{}]", wechatId, wechatInfo.getReqKey(),
				message);
		String decryptMessage = Crypt.getInstance().decrypt(message, wechatInfo.getReqKey());
		if (StringHelper.isBlank(decryptMessage)) return new HttpResult().failure("发送消息内容签名不正确");

		MsgBean msgBean = JSONObject.parseObject(decryptMessage, MsgBean.class);
		if (null == msgBean) return new HttpResult().failure("发送消息内容转换失败");

		WxTemplateEnum wxTemplateEnum = WxTemplateEnum.of(msgBean.getTemplateId());
		try {
			boolean isOk = messageService.sendWeixinTemplateMessage(wxTemplateEnum, msgBean);
			if (isOk) return new HttpResult().success("SUCCESS");
		} catch (WxErrorException e) {
			e.printStackTrace();
			return new HttpResult().failure("发送消息失败[" + e.getMessage() + "]");
		}

		return new HttpResult().failure("消息发送失败！");
	}

	@ResponseBody
	@RequestMapping(value = "/sendCurrency/{wechatId}")
	public HttpResult sendWeixinCurrencyTemplateMessage(@PathVariable("wechatId") String wechatId,
			@RequestParam("message") String message) {
		if (StringHelper.isBlank(message)) return new HttpResult().failure("发送消息内容不能为空");

		WechatInfo wechatInfo = wechatInfoService.findByWechatId(wechatId);
		if (null == wechatInfo)
			return new HttpResult().failure("公众号wechatId[" + wechatId + "]不存在");

		log.info("公众号消息发送：|wechatId[{}]|reqKey[{}]|message[{}]", wechatId, wechatInfo.getReqKey(), message);
		String decryptMessage = Crypt.getInstance().decrypt(message, wechatInfo.getReqKey());
		if (StringHelper.isBlank(decryptMessage)) return new HttpResult().failure("发送消息内容签名不正确");

		MsgBean msgBean = JSONObject.parseObject(decryptMessage, MsgBean.class);
		if (null == msgBean) return new HttpResult().failure("发送消息内容转换失败");
		try {
			boolean isOk = messageService.sendWeixinCurrencyTemplateMessage(msgBean);
			if (isOk) return new HttpResult().success("SUCCESS");
		} catch (WxErrorException e) {
			e.printStackTrace();
			return new HttpResult().failure("发送消息失败[" + e.getMessage() + "]");
		}

		return new HttpResult().failure("消息发送失败！");
	}
}

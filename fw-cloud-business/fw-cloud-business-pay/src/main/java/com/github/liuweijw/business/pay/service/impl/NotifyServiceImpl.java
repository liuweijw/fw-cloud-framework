package com.github.liuweijw.business.pay.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.liuweijw.business.pay.beans.NotifyBean;
import com.github.liuweijw.business.pay.config.RabbitConfiguration;
import com.github.liuweijw.business.pay.domain.PayMchInfo;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.MchInfoService;
import com.github.liuweijw.business.pay.service.NotifyService;
import com.github.liuweijw.commons.pay.beans.NotifyReqBean;
import com.github.liuweijw.commons.pay.utils.PayDigestUtil;
import com.github.liuweijw.commons.pay.utils.PayUtil;
import com.github.liuweijw.commons.utils.WebUtils;

@Slf4j
@Component
public class NotifyServiceImpl implements NotifyService {

	@Autowired
	private AmqpTemplate	rabbitTemplate;

	@Autowired
	private MchInfoService	mchInfoService;

	@Override
	public void notifyPayOrder(PayOrder payOrder) {
		NotifyBean notifyBean = new NotifyBean();
		notifyBean.setOrderId(payOrder.getPayOrderId());
		notifyBean.setMethod("GET");
		notifyBean.setUrl(buildNotifyUrl(payOrder));
		notifyBean.setCount(payOrder.getNotifyCount());
		notifyBean.setCreateTime(System.currentTimeMillis());
		rabbitTemplate.convertAndSend(RabbitConfiguration.PAY_NOTIFY_DELAY_QUENU_NAME, notifyBean);
	}

	@Override
	public void notifyPayOrder(NotifyBean notifyBean, int count) {
		rabbitTemplate.convertAndSend(RabbitConfiguration.PAY_NOTIFY_DELAY_PRE_MESSAGE_TTL,
				notifyBean, new MessagePostProcessor() {

					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setExpiration(count + "");
						message.getMessageProperties().setDeliveryMode(
								MessageDeliveryMode.PERSISTENT);
						return message;
					}
				});
	}

	private String buildNotifyUrl(PayOrder payOrder) {
		String mchId = payOrder.getMch_id();
		PayMchInfo mchInfo = mchInfoService.findMchInfoByMchId(mchId);
		String resKey = mchInfo.getResKey();

		NotifyReqBean notifyReqBean = new NotifyReqBean();
		notifyReqBean.setPayOrderId(payOrder.getPayOrderId()); // 支付订单号
		notifyReqBean.setMchId(payOrder.getMch_id()); // 商户ID
		notifyReqBean.setMchOrderNo(payOrder.getMchOrderNo()); // 商户订单号
		notifyReqBean.setChannelId(payOrder.getChannelId()); // 渠道ID
		notifyReqBean.setAmount(payOrder.getAmount()); // 支付金额
		notifyReqBean.setCurrency(payOrder.getCurrency()); // 货币类型
		notifyReqBean.setStatus(payOrder.getStatus()); // 支付状态
		notifyReqBean.setIp(payOrder.getIp()); // 客户端IP
		notifyReqBean.setDevice(WebUtils.buildURLEncoder(payOrder.getDevice())); // 设备
		notifyReqBean.setSubject(WebUtils.buildURLEncoder(payOrder.getSubject())); // 商品标题
		notifyReqBean.setChannelOrderNo(payOrder.getChannelOrderNo()); // 渠道订单号
		notifyReqBean.setParam1(WebUtils.buildURLEncoder(payOrder.getParam1())); // 扩展参数1
		notifyReqBean.setParam2(WebUtils.buildURLEncoder(payOrder.getParam2())); // 扩展参数2
		notifyReqBean.setPaySuccTime(payOrder.getPaySuccTime()); // 支付成功时间
		notifyReqBean.setBackType("2"); // backType 1：前台页面；2：后台接口

		JSONObject jsonObj = (JSONObject) JSON.toJSON(notifyReqBean);
		// 先对原文签名
		String reqSign = PayDigestUtil.getSign(jsonObj, resKey);
		jsonObj.put("sign", reqSign); // 签名

		String param = PayUtil.buildUrlParams(jsonObj);
		StringBuffer sb = new StringBuffer();
		sb.append(payOrder.getNotifyUrl()).append("?").append(param);

		log.info("notifyUrl:" + sb);
		return sb.toString();
	}

}

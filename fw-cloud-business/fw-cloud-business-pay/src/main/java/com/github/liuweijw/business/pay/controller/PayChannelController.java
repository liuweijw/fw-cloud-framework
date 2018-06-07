package com.github.liuweijw.business.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.commons.base.R;

/**
 * 支付渠道信息获取
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping(value = "/pay/channel")
public class PayChannelController {

	@Autowired
	private PayChannelService	payChannelService;

	@RequestMapping(value = "/find/{channelId}/{mchId}")
	public R<PayChannel> selectPayChannel(@PathVariable String channelId, @PathVariable String mchId) {

		PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);

		return new R<PayChannel>().data(payChannel);
	}
}

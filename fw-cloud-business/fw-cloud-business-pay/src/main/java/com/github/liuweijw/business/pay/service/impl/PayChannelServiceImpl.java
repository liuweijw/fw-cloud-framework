package com.github.liuweijw.business.pay.service.impl;

import org.springframework.stereotype.Component;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.domain.QPayChannel;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.commons.utils.StringHelper;

@Component
public class PayChannelServiceImpl extends JPAFactoryImpl implements PayChannelService {

	@Override
	public PayChannel findPayChannel(String channelId, String mchId) {
		if (StringHelper.isBlank(channelId) || StringHelper.isBlank(mchId)) return null;

		QPayChannel qPayChannel = QPayChannel.payChannel;
		return this.queryFactory.selectFrom(qPayChannel)
				.where(qPayChannel.channelId.eq(channelId.trim()))
				.where(qPayChannel.mchId.eq(mchId.trim()))
				.fetchFirst();
	}
}

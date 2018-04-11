package com.github.liuweijw.business.pay.service;

import com.github.liuweijw.business.pay.domain.PayChannel;

public interface PayChannelService {

	PayChannel findPayChannel(String channelId, String mchId);

}

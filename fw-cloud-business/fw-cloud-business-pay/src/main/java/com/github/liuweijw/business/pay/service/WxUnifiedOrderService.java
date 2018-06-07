package com.github.liuweijw.business.pay.service;

import java.util.Map;

import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.commons.base.R;

public interface WxUnifiedOrderService {

	public R<Map<String, Object>> doWxUnifiedOrderRequest(String tradeTypeApp, PayOrder payOrder,
			Map<String, String> params);

}

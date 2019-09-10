package com.github.liuweijw.business.pay.service;

import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.commons.base.R;

import java.util.Map;

public interface AliUnifiedOrderService {

    R<Map<String, Object>> doAliUnifiedOrderRequest(String tradeTypeApp, PayOrder payOrder,
                                                    Map<String, String> params);

}

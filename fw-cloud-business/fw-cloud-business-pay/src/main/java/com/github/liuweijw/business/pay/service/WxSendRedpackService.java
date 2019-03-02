package com.github.liuweijw.business.pay.service;

import java.util.Map;

import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.liuweijw.business.pay.domain.PaySendRedpack;
import com.github.liuweijw.commons.base.R;

public interface WxSendRedpackService {

	R<Map<String, Object>> sendRedpack(PaySendRedpack paySendRedpack, WxPaySendRedpackRequest build);

}

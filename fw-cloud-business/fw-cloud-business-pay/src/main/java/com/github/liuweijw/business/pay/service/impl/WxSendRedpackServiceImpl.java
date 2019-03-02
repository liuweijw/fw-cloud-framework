package com.github.liuweijw.business.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.result.WxPaySendRedpackResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.liuweijw.business.pay.domain.PaySendRedpack;
import com.github.liuweijw.business.pay.repository.PaySendRedpackRepository;
import com.github.liuweijw.business.pay.service.WxSendRedpackService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.pay.utils.PayUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WxSendRedpackServiceImpl implements WxSendRedpackService {

	@Autowired
	private PaySendRedpackRepository paySendRedpackRepository;

	@Override
	public R<Map<String, Object>> sendRedpack(PaySendRedpack paySendRedpack, WxPaySendRedpackRequest sendRedpackRequest) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 设置会出现签名加密返回
		returnMap.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
		returnMap.put("return_code", PayConstant.RETURN_VALUE_FAIL);
		returnMap.put("return_msg", "请求出现异常！");
		boolean isCommonRedPack = paySendRedpack.getRedPackType().intValue() == 0;
		String resKey = paySendRedpack.getResKey();
		String logPrefix = isCommonRedPack ? "【发放普通红包】" : "【发放裂变红包】";
		try {
			log.info(logPrefix + "请求：" + sendRedpackRequest.toString());
			WxPayService wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(paySendRedpack.getWxPayConfig());

			WxPaySendRedpackResult paySendRedpackResult = wxPayService.sendRedpack(sendRedpackRequest);
			paySendRedpack.setReturnCode(paySendRedpackResult.getReturnCode());
			paySendRedpack.setReturnMsg(paySendRedpackResult.getReturnMsg());
			paySendRedpack.setResultCode(paySendRedpackResult.getResultCode());
			paySendRedpack.setErrCode(paySendRedpackResult.getErrCode());
			paySendRedpack.setErrCodeDes(paySendRedpackResult.getErrCodeDes());

			// 订单流水号
			returnMap.put("send_order_id", paySendRedpack.getSendOrderId());
			// 订单编号
			returnMap.put("mch_order_no", paySendRedpack.getMchOrderNo());

			returnMap.put("return_code", paySendRedpackResult.getReturnCode());
			returnMap.put("return_msg", paySendRedpackResult.getReturnMsg());
			returnMap.put("result_code", paySendRedpackResult.getResultCode());
			returnMap.put("err_code", paySendRedpackResult.getErrCode());
			returnMap.put("err_code_des", paySendRedpackResult.getErrCodeDes());
			if (PayConstant.RETURN_VALUE_SUCCESS.equals(paySendRedpackResult.getReturnCode())
					&& PayConstant.RETURN_VALUE_SUCCESS.equals(paySendRedpackResult.getResultCode())) {
				paySendRedpack.setWxTotalAmount(paySendRedpackResult.getTotalAmount());
				paySendRedpack.setSendListid(paySendRedpackResult.getSendListid());
				paySendRedpack.setSendTime(paySendRedpackResult.getSendTime());
				returnMap.put("total_amount", paySendRedpackResult.getTotalAmount());
				returnMap.put("send_listid", paySendRedpackResult.getSendListid());
				returnMap.put("send_time", paySendRedpackResult.getSendTime());
				log.info(logPrefix + "响应：" + JSON.toJSONString(returnMap));
				log.info(logPrefix + "结果：" + PayConstant.RETURN_VALUE_SUCCESS);
			} else {
				log.info(logPrefix + "响应：" + JSON.toJSONString(returnMap));
				log.info(logPrefix + "结果：" + PayConstant.RETURN_VALUE_FAIL);
			}
			// 保存 发送明细数据入库
			paySendRedpackRepository.saveAndFlush(paySendRedpack);

			return new R<Map<String, Object>>().data(PayUtil.makeRetData(returnMap, resKey)).success();
		} catch (WxPayException e) {
			e.printStackTrace();
			return new R<Map<String, Object>>().data(PayUtil.makeRetData(returnMap, resKey)).failure(logPrefix + "请求异常：" + e.toString());
		}
	}
}

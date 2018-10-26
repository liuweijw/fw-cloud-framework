package com.github.liuweijw.business.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.github.liuweijw.business.pay.config.wechat.WxPayProperties;
import com.github.liuweijw.business.pay.config.wechat.WxPayUtil;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.business.pay.service.WxUnifiedOrderService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.pay.enums.PayEnum;
import com.github.liuweijw.commons.pay.utils.PayUtil;
import com.github.liuweijw.commons.utils.StringHelper;

@Slf4j
@Component
public class WxUnifiedOrderServiceImpl implements WxUnifiedOrderService {

	@Autowired
	private WxPayProperties	wxPayProperties;

	@Autowired
	private PayOrderService	payOrderService;

	@Override
	public R<Map<String, Object>> doWxUnifiedOrderRequest(String tradeType, PayOrder payOrder,
			Map<String, String> params) {

		try {
			String logPrefix = "【微信支付统一下单】";
			if (null == payOrder || null == params || StringHelper.isBlank(tradeType)
					|| StringHelper.isBlank(params.get("resKey"))
					|| StringHelper.isBlank(params.get("channelParam")))
				return new R<Map<String, Object>>().data(
						PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "",
								PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001)).failure();

			String resKey = params.get("resKey");
			WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(params.get("channelParam"),
					tradeType, wxPayProperties.getCertRootPath(), wxPayProperties.getNotifyUrl());
			WxPayService wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(wxPayConfig);
			WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(payOrder,
					wxPayConfig);
			String payOrderId = payOrder.getPayOrderId();
			WxPayUnifiedOrderResult wxPayUnifiedOrderResult = null;
			try {
				wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
				log.info("{} >>> 下单成功", logPrefix);
				Map<String, Object> map = PayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
						PayConstant.RETURN_VALUE_SUCCESS, null);
				map.put("payOrderId", payOrderId);
				map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
				boolean result = payOrderService.updatePayOrderStatus4Paying(payOrderId,
						wxPayUnifiedOrderResult.getPrepayId());
				log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrderId,
						wxPayUnifiedOrderResult.getPrepayId(), result);
				switch (tradeType) {
				case PayConstant.WxConstant.TRADE_TYPE_NATIVE: {
					map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL()); // 二维码支付链接
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_APP: {
					Map<String, String> payInfo = new HashMap<>();
					String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
					String nonceStr = String.valueOf(System.currentTimeMillis());
					// APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
					String appId = wxPayConfig.getAppId();
					Map<String, String> configMap = new HashMap<>();
					// 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
					String partnerId = wxPayConfig.getMchId();
					configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
					configMap.put("partnerid", partnerId);
					String packageValue = "Sign=WXPay";
					configMap.put("package", packageValue);
					configMap.put("timestamp", timestamp);
					configMap.put("noncestr", nonceStr);
					configMap.put("appid", appId);
					// 此map用于客户端与微信服务器交互
					payInfo.put("sign", SignUtils.createSign(configMap, null, wxPayConfig
							.getMchKey(), new String[0]));
					payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
					payInfo.put("partnerId", partnerId);
					payInfo.put("appId", appId);
					payInfo.put("packageValue", packageValue);
					payInfo.put("timeStamp", timestamp);
					payInfo.put("nonceStr", nonceStr);
					map.put("payParams", payInfo);
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_JSPAI: {
					Map<String, String> payInfo = new HashMap<>();
					String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
					String nonceStr = String.valueOf(System.currentTimeMillis());
					payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
					// 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
					payInfo.put("timeStamp", timestamp);
					payInfo.put("nonceStr", nonceStr);
					payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
					payInfo.put("signType", WxPayConstants.SignType.MD5);
					payInfo.put("paySign", SignUtils.createSign(payInfo, null, wxPayConfig
							.getMchKey(), new String[0]));
					map.put("payParams", payInfo);
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_MWEB: {
					map.put("payUrl", wxPayUnifiedOrderResult.getMwebUrl()); // h5支付链接地址
					break;
				}
				}
				return new R<Map<String, Object>>().data(PayUtil.makeRetData(map, resKey))
						.success();
			} catch (WxPayException e) {
				log.error(e + "下单失败");
				// 出现业务错误
				log.info("{}下单返回失败", logPrefix);
				log.info("err_code:{}", e.getErrCode());
				log.info("err_code_des:{}", e.getErrCodeDes());
				return new R<Map<String, Object>>().data(
						PayUtil.makeRetData(PayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS,
								"", PayConstant.RETURN_VALUE_FAIL, "0111", "调用微信支付失败,"
										+ e.getErrCode() + ":" + e.getErrCodeDes()), resKey))
						.failure();
			}
		} catch (Exception e) {
			log.error("微信支付统一下单异常" + e);
			return new R<Map<String, Object>>().data(
					PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "",
							PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001)).failure();
		}
	}

	/**
	 * 构建微信统一下单请求数据
	 */
	private WxPayUnifiedOrderRequest buildUnifiedOrderRequest(PayOrder payOrder,
			WxPayConfig wxPayConfig) {
		String tradeType = wxPayConfig.getTradeType();
		String payOrderId = payOrder.getPayOrderId();
		Integer totalFee = payOrder.getAmount().intValue();// 支付金额,单位分
		String deviceInfo = payOrder.getDevice();
		String body = payOrder.getBody();
		String detail = null;
		String attach = null;
		String outTradeNo = payOrderId;
		String feeType = "CNY";
		String spBillCreateIP = payOrder.getIp();
		String timeStart = null;
		String timeExpire = null;
		String goodsTag = null;
		String notifyUrl = wxPayConfig.getNotifyUrl();
		String productId = null;
		if (tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE))
			productId = JSON.parseObject(payOrder.getExtra()).getString("productId");
		String limitPay = null;
		String openId = null;
		if (tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI))
			openId = JSON.parseObject(payOrder.getExtra()).getString("openId");
		String sceneInfo = null;
		if (tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB))
			sceneInfo = JSON.parseObject(payOrder.getExtra()).getString("sceneInfo");
		// 微信统一下单请求对象
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setDeviceInfo(deviceInfo);
		request.setBody(body);
		request.setDetail(detail);
		request.setAttach(attach);
		request.setOutTradeNo(outTradeNo);
		request.setFeeType(feeType);
		request.setTotalFee(totalFee);
		request.setSpbillCreateIp(spBillCreateIP);
		request.setTimeStart(timeStart);
		request.setTimeExpire(timeExpire);
		request.setGoodsTag(goodsTag);
		request.setNotifyUrl(notifyUrl);
		request.setTradeType(tradeType);
		request.setProductId(productId);
		request.setLimitPay(limitPay);
		request.setOpenid(openId);
		request.setSceneInfo(sceneInfo);

		return request;
	}
}

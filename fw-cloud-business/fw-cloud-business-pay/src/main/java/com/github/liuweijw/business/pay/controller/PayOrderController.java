package com.github.liuweijw.business.pay.controller;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.liuweijw.business.commons.utils.SequenceUtils;
import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.domain.PayMchInfo;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.MchInfoService;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.business.pay.service.WxUnifiedOrderService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.pay.beans.PayUnifiedOrder;
import com.github.liuweijw.commons.pay.constants.BizConstant;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.pay.utils.PayUtil;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;

/**
 * 支付订单,包括:统一下单,订单查询,补单等接口
 * 
 * @author liuweijw
 */
@Slf4j
@RestController
@RequestMapping(value = "/pay/order")
public class PayOrderController {

	@Autowired
	private PayOrderService			payOrderService;

	@Autowired
	private MchInfoService			mchInfoService;

	@Autowired
	private PayChannelService		payChannelService;

	@Autowired
	private WxUnifiedOrderService	wxUnifiedOrderService;

	/**
	 * 统一下单接口: 1) 先验证接口参数以及签名信息 2) 验证通过创建支付订单 3) 根据商户选择渠道,调用支付服务进行下单 4) 返回下单数据
	 */
	@RequestMapping(value = "/create")
	public R<Map<String, Object>> create(@RequestBody PayUnifiedOrder unifiedOrder) {
		PayOrder payOrder = new PayOrder();
		Map<String, String> params = new HashMap<String, String>();
		R<Boolean> validateResult = validatePayOrderParams(unifiedOrder, payOrder, params);
		if (!validateResult.getData()) {
			log.error(JSON.toJSONString(validateResult));
			return new R<Map<String, Object>>().data(
					PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "订单校验失败", null, null))
					.failure();
		}

		PayOrder resultOrder = payOrderService.updateOrSavePayOrder(payOrder);
		if (null == resultOrder)
			return new R<Map<String, Object>>().data(
					PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "订单保存失败", null, null))
					.failure();

		switch (resultOrder.getChannelId()) {
		case PayConstant.PAY_CHANNEL_WX_APP:
			return wxUnifiedOrderService.doWxUnifiedOrderRequest(
					PayConstant.WxConstant.TRADE_TYPE_APP, payOrder, params);
		case PayConstant.PAY_CHANNEL_WX_JSAPI:
			return wxUnifiedOrderService.doWxUnifiedOrderRequest(
					PayConstant.WxConstant.TRADE_TYPE_JSPAI, payOrder, params);
		case PayConstant.PAY_CHANNEL_WX_NATIVE:
			return wxUnifiedOrderService.doWxUnifiedOrderRequest(
					PayConstant.WxConstant.TRADE_TYPE_NATIVE, payOrder, params);
		case PayConstant.PAY_CHANNEL_WX_MWEB:
			return wxUnifiedOrderService.doWxUnifiedOrderRequest(
					PayConstant.WxConstant.TRADE_TYPE_MWEB, payOrder, params);
		default:
			break;
		}
		return new R<Map<String, Object>>().data(
				PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "不支持的支付渠道类型[channelId="
						+ resultOrder.getChannelId() + "]", null, null)).failure();
	}

	@RequestMapping(value = "/find/{mchId}")
	public R<PayOrder> query(@PathVariable String mchId, String payOrderId, String mchOrderNo,
			Boolean isNotify) {

		PayOrder payOrder = null;
		if (StringHelper.isNotBlank(payOrderId)) {
			payOrder = payOrderService.findPayOrderByMchIdAndPayOrderId(mchId, payOrderId);
		} else {
			payOrder = payOrderService.findPayOrderByMchIdAndMchOrderNo(mchId, mchOrderNo);
		}

		if (null == payOrder) return new R<PayOrder>().failure("支付订单不存在");

		if (null != isNotify && isNotify && payOrder.getStatus() == PayConstant.PAY_STATUS_SUCCESS) {
			// 如果选择回调且支付状态为支付成功,则回调业务系统
		}

		return new R<PayOrder>().data(payOrder).success("更新成功");
	}

	private R<Boolean> validatePayOrderParams(PayUnifiedOrder unifiedOrder, PayOrder payOrder,
			Map<String, String> params) {
		String preFix = "[统一下单提示] ";
		if (null == unifiedOrder)
			return new R<Boolean>().data(false).failure(preFix + "下单信息不能为空！");

		// 商户id
		String mchId = unifiedOrder.getMchId();
		if (StringHelper.isBlank(mchId))
			return new R<Boolean>().data(false).failure(preFix + "商户ID不存在！");

		// 商户订单号
		String mchOrderNo = unifiedOrder.getMchOrderNo();
		if (StringHelper.isBlank(mchOrderNo))
			return new R<Boolean>().data(false).failure(preFix + "商户订单号不存在！");

		// 渠道ID
		String channelId = unifiedOrder.getChannelId();
		if (StringHelper.isBlank(channelId))
			return new R<Boolean>().data(false).failure(preFix + "渠道ID不存在！");

		// 支付金额（单位分）
		Long amount = unifiedOrder.getAmount();
		if (null == amount || amount <= 0)
			return new R<Boolean>().data(false).failure(preFix + "支付金额（单位分）不正确！");

		// 币种
		String currency = unifiedOrder.getCurrency();
		if (StringHelper.isBlank(currency))
			return new R<Boolean>().data(false).failure(preFix + "币种未设置！");

		// 支付结果回调URL
		String notifyUrl = unifiedOrder.getNotifyUrl();
		if (StringHelper.isBlank(notifyUrl))
			return new R<Boolean>().data(false).failure(preFix + "支付回调未设置！");

		// 商品主题
		String subject = unifiedOrder.getSubject();
		if (StringHelper.isBlank(subject))
			return new R<Boolean>().data(false).failure(preFix + "商品主题未设置！");

		// 商品描述信息
		String body = unifiedOrder.getBody();
		if (StringHelper.isBlank(body))
			return new R<Boolean>().data(false).failure(preFix + "商品描述信息未设置！");

		// ip
		String ip = unifiedOrder.getIp();
		if (StringHelper.isBlank(ip))
			return new R<Boolean>().data(false).failure(preFix + "请求IP地址不正确！");

		String device = unifiedOrder.getDevice();
		if (StringHelper.isBlank(device))
			return new R<Boolean>().data(false).failure(preFix + "请求设备信息设置不正确！");

		String extra = unifiedOrder.getExtra();
		// 根据不同渠道,判断extra参数
		if (PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
			if (StringHelper.isBlank(extra))
				return new R<Boolean>().data(false).failure(preFix + channelId + "[extra]信息未设置！");

			JSONObject extraObject = JSON.parseObject(extra);
			if (null == extraObject || !extraObject.containsKey("openId"))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.openId]信息未设置！");

			String openId = extraObject.getString("openId");
			if (StringHelper.isBlank(openId))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.openId]信息未正确设置！");

		} else if (PayConstant.PAY_CHANNEL_WX_NATIVE.equalsIgnoreCase(channelId)) {
			if (StringHelper.isBlank(extra))
				return new R<Boolean>().data(false).failure(preFix + channelId + "[extra]信息未设置！");

			JSONObject extraObject = JSON.parseObject(extra);
			if (null == extraObject || !extraObject.containsKey("productId"))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.productId]信息未设置！");

			String productId = extraObject.getString("productId");
			if (StringHelper.isBlank(productId))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.productId]信息未正确设置！");

		} else if (PayConstant.PAY_CHANNEL_WX_MWEB.equalsIgnoreCase(channelId)) {
			if (StringHelper.isBlank(extra))
				return new R<Boolean>().data(false).failure(preFix + channelId + "[extra]信息未设置！");

			JSONObject extraObject = JSON.parseObject(extra);
			if (null == extraObject || !extraObject.containsKey("sceneInfo"))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.sceneInfo]信息未设置！");

			String sceneInfo = extraObject.getString("sceneInfo");
			if (StringHelper.isBlank(sceneInfo))
				return new R<Boolean>().data(false).failure(
						preFix + channelId + "[extra.sceneInfo]信息未正确设置！");
		}

		// 签名信息
		String sign = unifiedOrder.getSign();
		if (StringHelper.isBlank(sign))
			return new R<Boolean>().data(false).failure(preFix + "未签名！");

		// 查询商户信息
		PayMchInfo mchInfo = mchInfoService.findMchInfoByMchId(mchId);
		if (null == mchInfo) return new R<Boolean>().data(false).failure(preFix + "商户信息不存在！");

		if (mchInfo.getStatu().intValue() != 1)
			return new R<Boolean>().data(false).failure(preFix + "商户信息已失效！");

		if (StringHelper.isBlank(mchInfo.getReqKey()))
			return new R<Boolean>().data(false).failure(preFix + "商户请求私钥未设置！");

		// 查询商户对应的支付渠道
		PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
		if (null == payChannel)
			return new R<Boolean>().data(false).failure(
					preFix + "商户渠道[channelId=" + channelId + ",mchId=" + mchId + "]信息不存在！");

		if (payChannel.getStatu().intValue() != 1)
			return new R<Boolean>().data(false).failure(
					preFix + "商户渠道[channelId=" + channelId + ",mchId=" + mchId + "]信息已经失效！");

		// 验证签名数据
		boolean verifyFlag = PayUtil.verifyPaySign((JSONObject) JSON.toJSON(unifiedOrder), mchInfo
				.getReqKey());
		if (!verifyFlag) return new R<Boolean>().data(false).failure(preFix + "下单信息验证签名失败！");

		payOrder.setPayOrderId(SequenceUtils.getInstance().generateBizSeqNo(
				BizConstant.PAY_ORDER_PREFIX));
		payOrder.setMch_id(mchInfo.getMchId());
		payOrder.setMchOrderNo(mchOrderNo);
		payOrder.setChannelId(channelId);
		payOrder.setAmount(amount);
		payOrder.setCurrency(currency);
		payOrder.setIp(ip);
		payOrder.setDevice(device);
		payOrder.setSubject(WebUtils.buildURLDecoder(subject));
		payOrder.setBody(WebUtils.buildURLDecoder(body));
		payOrder.setExtra(extra);
		payOrder.setChannelMchId(payChannel.getChannelMchId());
		payOrder.setParam1(unifiedOrder.getParam1());
		payOrder.setParam2(unifiedOrder.getParam2());
		payOrder.setNotifyUrl(notifyUrl);

		params.put("resKey", mchInfo.getResKey());
		params.put("channelParam", payChannel.getParam());

		return new R<Boolean>().data(true).success(preFix + "下单信息验证成功！");
	}
}

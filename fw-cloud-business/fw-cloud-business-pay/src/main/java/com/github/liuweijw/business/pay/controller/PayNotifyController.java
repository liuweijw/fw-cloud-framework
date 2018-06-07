package com.github.liuweijw.business.pay.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.liuweijw.business.pay.config.wechat.WxPayUtil;
import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.NotifyService;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.utils.StringHelper;

/**
 * 支付后台通知
 * 
 * @author liuweijw
 */
@Slf4j
@RestController
@RequestMapping(value = "/pay/notify")
public class PayNotifyController {

	@Autowired
	private PayOrderService		payOrderService;

	@Autowired
	private PayChannelService	payChannelService;

	@Autowired
	private NotifyService		notifyService;

	/**
	 * 微信支付(统一下单接口)后台通知响应
	 */
	@RequestMapping("/result")
	@ResponseBody
	public String notifyResult(HttpServletRequest request, HttpServletResponse response) {
		log.info("====== 开始接收微信支付回调通知 ======");
		String result = doNotifyResult(request, response);
		log.info("响应给微信:{}", result);
		log.info("====== 完成接收微信支付回调通知 ======");
		return result;
	}

	@RequestMapping("/notifyTest/{payOrderId}")
	@ResponseBody
	public String notifyTest(@PathVariable String payOrderId) {
		String logPrefix = "【微信支付回调通知】";
		log.info("====== 开始接收微信支付回调通知 ======");
		PayOrder payOrder = payOrderService.findPayOrderByOrderId(payOrderId);
		if (null == payOrder) {
			log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
			return WxPayNotifyResponse.fail("未查询到相应订单信息");
		}

		// 查询payChannel记录
		String mchId = payOrder.getMch_id();
		String channelId = payOrder.getChannelId();
		PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
		if (null == payChannel) {
			log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId, mchId,
					channelId);
			return WxPayNotifyResponse.fail("未查询到订单相关渠道信息");
		}

		// 处理订单
		int payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
		if (payStatus == PayConstant.PAY_STATUS_COMPLETE) { // 处理完成
			log.info("====== 订单已经完成直接返回 ======");
			return WxPayNotifyResponse.success("OK");
		}

		if (payStatus != PayConstant.PAY_STATUS_SUCCESS) {
			boolean updatePayOrderRows = payOrderService.updatePayOrderStatus4Success(payOrder
					.getPayOrderId());
			if (!updatePayOrderRows) {
				log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder
						.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
				return WxPayNotifyResponse.fail("处理订单失败");
			}
			log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder
					.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
			payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
		}
		// 业务系统后端通知
		this.notifyService.notifyPayOrder(payOrder);
		log.info("====== 完成接收微信支付回调通知 ======");
		return WxPayNotifyResponse.success("OK");
	}

	public String doNotifyResult(HttpServletRequest request, HttpServletResponse response) {
		String logPrefix = "【微信支付回调通知】";
		log.info("====== 开始接收微信支付回调通知 ======");
		try {
			String xmlResult = IOUtils.toString(request.getInputStream(), request
					.getCharacterEncoding());
			log.info("{}通知请求数据:reqStr={}", logPrefix, xmlResult);
			if (StringHelper.isBlank(xmlResult)) { return WxPayNotifyResponse.fail("FAIL"); }
			WxPayServiceImpl wxPayService = new WxPayServiceImpl();
			WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);

			// 验证业务数据是否正确
			if (!PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(result.getResultCode())
					|| !PayConstant.RETURN_VALUE_SUCCESS.equalsIgnoreCase(result.getResultCode())) {
				log.error("returnCode={},resultCode={},errCode={},errCodeDes={}", result
						.getReturnCode(), result.getResultCode(), result.getErrCode(), result
						.getErrCodeDes());
				return WxPayNotifyResponse.fail("notify data failed");
			}

			Integer total_fee = result.getTotalFee(); // 总金额
			String out_trade_no = result.getOutTradeNo(); // 商户系统订单号

			// 查询payOrder记录
			String payOrderId = out_trade_no;
			PayOrder payOrder = payOrderService.findPayOrderByOrderId(payOrderId);
			if (null == payOrder) {
				log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
				return WxPayNotifyResponse.fail("未查询到相应订单信息");
			}

			// 查询payChannel记录
			String mchId = payOrder.getMch_id();
			String channelId = payOrder.getChannelId();
			PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
			if (null == payChannel) {
				log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId,
						mchId, channelId);
				return WxPayNotifyResponse.fail("未查询到订单相关渠道信息");
			}
			WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannel.getParam());
			wxPayService.setConfig(wxPayConfig);
			// 签名校验
			result.checkResult(wxPayService, null, false);

			// 核对金额
			long wxPayAmt = new BigDecimal(total_fee).longValue();
			long dbPayAmt = payOrder.getAmount().longValue();
			if (dbPayAmt != wxPayAmt) {
				log.error(
						"db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}",
						total_fee, payOrderId);
				return WxPayNotifyResponse.fail("支付金额不正确");
			}

			// 处理订单
			int payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
			if (payStatus == PayConstant.PAY_STATUS_COMPLETE) { // 处理完成
				log.info("====== 订单已经完成直接返回 ======");
				return WxPayNotifyResponse.success("OK");
			}

			if (payStatus != PayConstant.PAY_STATUS_SUCCESS) {
				boolean updatePayOrderRows = payOrderService.updatePayOrderStatus4Success(payOrder
						.getPayOrderId());
				if (!updatePayOrderRows) {
					log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder
							.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
					return WxPayNotifyResponse.fail("处理订单失败");
				}
				log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder
						.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
				payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
			}
			// 业务系统后端通知
			this.notifyService.notifyPayOrder(payOrder);
			log.info("====== 完成接收微信支付回调通知 ======");
			return WxPayNotifyResponse.success("OK");
		} catch (WxPayException e) {
			// 出现业务错误
			log.error("微信回调结果异常,异常原因" + e);
			log.info("{}请求数据result_code=FAIL", logPrefix);
			log.info("err_code:", e.getErrCode());
			log.info("err_code_des:", e.getErrCodeDes());
			return WxPayNotifyResponse.fail(e.getMessage());
		} catch (Exception e) {
			log.error("微信回调结果异常,异常原因" + e);
			return WxPayNotifyResponse.fail(e.getMessage());
		}
	}
}

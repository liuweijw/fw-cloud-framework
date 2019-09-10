package com.github.liuweijw.business.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.github.liuweijw.business.commons.utils.AmountUtil;
import com.github.liuweijw.business.pay.config.alipay.AlipayConfig;
import com.github.liuweijw.business.pay.config.alipay.AlipayProperties;
import com.github.liuweijw.business.pay.config.alipay.AlipayUtil;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.AliUnifiedOrderService;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.pay.enums.PayEnum;
import com.github.liuweijw.commons.pay.utils.PayUtil;
import com.github.liuweijw.commons.utils.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AliUnifiedOrderServiceImpl implements AliUnifiedOrderService {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PayOrderService payOrderService;

    @Override
    public R<Map<String, Object>> doAliUnifiedOrderRequest(String tradeType, PayOrder payOrder,
                                                           Map<String, String> params) {

        try {
            if (null == payOrder || null == params || StringHelper.isBlank(tradeType)
                    || StringHelper.isBlank(params.get("resKey"))
                    || StringHelper.isBlank(params.get("channelParam")))
                return new R<Map<String, Object>>().data(
                        PayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "", PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001))
                        .failure();

            String resKey = params.get("resKey");
            String payOrderId = payOrder.getPayOrderId();

            AlipayConfig alipayConfig = AlipayUtil.init(params.get("channelParam"), params.get("returnUrl"));
            AlipayClient client = new DefaultAlipayClient(
                    alipayConfig.getUrl(), alipayConfig.getAppId(), alipayConfig.getRsaPrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

            Map<String, Object> map = PayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "", PayConstant.RETURN_VALUE_SUCCESS, null);
            if (tradeType.equals(PayConstant.PAY_CHANNEL_ALIPAY_WAP)) {
                String logPrefix = "【支付宝WAP支付下单】";
                AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
                model.setOutTradeNo(payOrderId);
                model.setSubject(payOrder.getSubject());
                model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
                model.setBody(payOrder.getBody());
                model.setProductCode("QUICK_WAP_PAY");
                String objParams = payOrder.getExtra();
                if (StringUtils.isNotEmpty(objParams)) {
                    JSONObject objParamsJson = JSON.parseObject(objParams);
                    if (StringUtils.isNotBlank(objParamsJson.getString("quit_url"))) {
                        model.setQuitUrl(objParamsJson.getString("quit_url"));
                    }
                }
                AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
                alipay_request.setBizModel(model);
                alipay_request.setNotifyUrl(alipayProperties.getNotifyUrl());
                alipay_request.setReturnUrl(alipayConfig.getReturnUrl());
                AlipayTradeWapPayResponse alipayTradeWapPayResponse = client.pageExecute(alipay_request);
                String payUrl = alipayTradeWapPayResponse.getBody();
                log.info("{}生成跳转路径：payUrl={}", logPrefix, payUrl);
                boolean result = payOrderService.updatePayOrderStatus4Paying(payOrderId, payOrderId);
                log.info("{}更新第三方支付订单号:payOrderId={},tradeNo={},result={}", logPrefix, payOrderId, alipayTradeWapPayResponse.getTradeNo(), result);
                log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
                map.put("tradeNo", alipayTradeWapPayResponse.getTradeNo());
                map.put("payOrderId", payOrderId);
                map.put("payUrl", payUrl);
                log.info("{}商户统一下单处理完成", logPrefix);
            } else if (tradeType.equals(PayConstant.PAY_CHANNEL_ALIPAY_PC)) {
                String logPrefix = "【支付宝PC支付下单】";
                AlipayTradePagePayModel model = new AlipayTradePagePayModel();
                model.setOutTradeNo(payOrderId);
                model.setSubject(payOrder.getSubject());
                model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
                model.setBody(payOrder.getBody());
                model.setProductCode("FAST_INSTANT_TRADE_PAY");
                String objParams = payOrder.getExtra();
                String qr_pay_mode = "2";
                String qrcode_width = "200";
                if (StringUtils.isNotEmpty(objParams)) {
                    JSONObject objParamsJson = JSON.parseObject(objParams);
                    qr_pay_mode = objParamsJson.containsKey("qr_pay_mode") ? objParamsJson.getString("qr_pay_mode") : qr_pay_mode;
                    qrcode_width = objParamsJson.containsKey("qrcode_width") ? objParamsJson.getString("qrcode_width") : qrcode_width;
                }
                model.setQrPayMode(qr_pay_mode);
                model.setQrcodeWidth(Long.parseLong(qrcode_width));
                AlipayTradePagePayRequest alipay_request = new AlipayTradePagePayRequest();
                alipay_request.setBizModel(model);
                alipay_request.setNotifyUrl(alipayProperties.getNotifyUrl());
                alipay_request.setReturnUrl(alipayConfig.getReturnUrl());
                AlipayTradePagePayResponse alipayTradePagePayResponse = client.pageExecute(alipay_request);
                String payUrl = alipayTradePagePayResponse.getBody();
                log.info("{}生成跳转路径：payUrl={}", logPrefix, payUrl);
                boolean result = payOrderService.updatePayOrderStatus4Paying(payOrderId, payOrderId);
                log.info("{}更新第三方支付订单号:payOrderId={},tradeNo={},result={}", logPrefix, payOrderId, alipayTradePagePayResponse.getTradeNo(), result);
                log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
                map.put("tradeNo", alipayTradePagePayResponse.getTradeNo());
                map.put("payOrderId", payOrderId);
                map.put("payUrl", payUrl);
                log.info("{}商户统一下单处理完成", logPrefix);
            } else if (tradeType.equals(PayConstant.PAY_CHANNEL_ALIPAY_MOBILE)) {
                String logPrefix = "【支付宝APP支付下单】";
                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setOutTradeNo(payOrderId);
                model.setSubject(payOrder.getSubject());
                model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
                model.setBody(payOrder.getBody());
                model.setProductCode("QUICK_MSECURITY_PAY");
                AlipayTradeAppPayRequest alipay_request = new AlipayTradeAppPayRequest();
                alipay_request.setBizModel(model);
                alipay_request.setNotifyUrl(alipayProperties.getNotifyUrl());
                alipay_request.setReturnUrl(alipayConfig.getReturnUrl());
                AlipayTradeAppPayResponse alipayTradeAppPayResponse = client.sdkExecute(alipay_request);
                String payParams = alipayTradeAppPayResponse.getBody();
                boolean result = payOrderService.updatePayOrderStatus4Paying(payOrderId, payOrderId);
                log.info("{}更新第三方支付订单号:payOrderId={},tradeNo={},result={}", logPrefix, payOrderId, alipayTradeAppPayResponse.getTradeNo(), result);
                log.info("{}生成请求支付宝数据,payParams={}", logPrefix, payParams);
                map.put("tradeNo", alipayTradeAppPayResponse.getTradeNo());
                map.put("payOrderId", payOrderId);
                map.put("payParams", payParams);
                log.info("{}商户统一下单处理完成", logPrefix);
            } else if (tradeType.equals(PayConstant.PAY_CHANNEL_ALIPAY_QR)) { // 刷脸付 需要签约
                String logPrefix = "【支付宝当面付之扫码支付下单】";
                AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
                model.setOutTradeNo(payOrderId);
                model.setSubject(payOrder.getSubject());
                model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
                model.setBody(payOrder.getBody());
                String objParams = payOrder.getExtra();
                if (StringUtils.isNotEmpty(objParams)) {
                    JSONObject objParamsJson = JSON.parseObject(objParams);
                    if (StringUtils.isNotBlank(objParamsJson.getString("discountable_amount"))) {
                        // 可打折金额
                        model.setDiscountableAmount(objParamsJson.getString("discountable_amount"));
                    }
                    if (StringUtils.isNotBlank(objParamsJson.getString("undiscountable_amount"))) {
                        // 不可打折金额
                        model.setUndiscountableAmount(objParamsJson.getString("undiscountable_amount"));
                    }
                }
                AlipayTradePrecreateRequest alipay_request = new AlipayTradePrecreateRequest();
                alipay_request.setBizModel(model);
                alipay_request.setNotifyUrl(alipayProperties.getNotifyUrl());
                alipay_request.setReturnUrl(alipayConfig.getReturnUrl());
                String payUrl = client.execute(alipay_request).getBody();
                log.info("{}生成跳转路径：payUrl={}", logPrefix, payUrl);
                boolean result = payOrderService.updatePayOrderStatus4Paying(payOrderId, payOrderId);
                log.info("{}更新第三方支付订单号:payOrderId={},result={}", logPrefix, payOrderId, result);
                log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
                map.put("payOrderId", payOrderId);
                map.put("payUrl", payUrl);
                log.info("{}商户统一下单处理完成", logPrefix);
            }
            return new R<Map<String, Object>>().data(PayUtil.makeRetData(map, resKey))
                    .success();
        } catch (Exception e) {
            log.error("支付宝支付统一下单异常" + e);
            return new R<Map<String, Object>>().data(
                    PayUtil.makeRetMap(
                            PayConstant.RETURN_VALUE_FAIL, "",
                            PayConstant.RETURN_VALUE_FAIL, PayEnum.ERR_0001))
                    .failure();
        }
    }
}

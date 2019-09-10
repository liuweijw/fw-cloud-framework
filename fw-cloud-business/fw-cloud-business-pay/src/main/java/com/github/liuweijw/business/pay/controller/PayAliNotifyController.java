package com.github.liuweijw.business.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.liuweijw.business.pay.config.alipay.AlipayConfig;
import com.github.liuweijw.business.pay.config.alipay.AlipayUtil;
import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.service.NotifyService;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付后台通知
 *
 * @author liuweijw
 */
@Slf4j
@RestController
@RequestMapping(value = "/pay/alinotify")
public class PayAliNotifyController {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayChannelService payChannelService;

    @Autowired
    private NotifyService notifyService;

    /**
     * 支付宝支付(统一下单接口)后台通知响应
     */
    @RequestMapping("/result")
    @ResponseBody
    public String notifyResult(HttpServletRequest request, HttpServletResponse response) {
        log.info("====== 开始接收支付宝支付回调通知 ======");
        String result = doNotifyResult(request, response);
        log.info("响应给支付宝:{}", result);
        log.info("====== 完成接收支付宝支付回调通知 ======");
        return result;
    }

    @RequestMapping("/notifyTest/{payOrderId}")
    @ResponseBody
    public String notifyTest(@PathVariable String payOrderId) {
        String logPrefix = "【支付宝支付回调通知】";
        log.info("====== 开始接收支付宝支付回调通知 ======");
        PayOrder payOrder = payOrderService.findPayOrderByOrderId(payOrderId);
        if (null == payOrder) {
            log.error("Can't found payOrder form db. payOrderId={}, ", payOrderId);
            return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
        }

        // 查询payChannel记录
        String mchId = payOrder.getMch_id();
        String channelId = payOrder.getChannelId();
        PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
        if (null == payChannel) {
            log.error("Can't found payChannel form db. mchId={} channelId={}, ", payOrderId, mchId, channelId);
            return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
        }

        // 处理订单
        int payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
        if (payStatus == PayConstant.PAY_STATUS_COMPLETE) { // 处理完成
            log.info("====== 订单已经完成直接返回 ======");
            return PayConstant.RETURN_ALIPAY_VALUE_SUCCESS;
        }

        if (payStatus != PayConstant.PAY_STATUS_SUCCESS) {
            boolean updatePayOrderRows = payOrderService.updatePayOrderStatus4Success(payOrder.getPayOrderId());
            if (!updatePayOrderRows) {
                log.error("{}更新支付状态失败,将payOrderId={},更新失败", logPrefix, payOrder.getPayOrderId());
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }
            log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
            payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
        }
        // 业务系统后端通知
        this.notifyService.notifyPayOrder(payOrder);
        log.info("====== 完成接收支付宝支付回调通知 ======");
        return PayConstant.RETURN_ALIPAY_VALUE_SUCCESS;
    }

    public String doNotifyResult(HttpServletRequest request, HttpServletResponse response) {
        String logPrefix = "【支付宝支付回调通知】";
        log.info("====== 开始接收支付宝支付回调通知 ======");
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            log.info("{}通知请求数据:reqStr={}", logPrefix, params);
            if (params.isEmpty()) {
                log.error("{}请求参数为空", logPrefix);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }

            String out_trade_no = params.get("out_trade_no");        // 商户订单号
            String total_amount = params.get("total_amount");        // 支付金额
            if (StringUtils.isEmpty(out_trade_no)) {
                log.error("{}AliPay Notify parameter out_trade_no is empty. out_trade_no={}", logPrefix, out_trade_no);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }
            if (StringUtils.isEmpty(total_amount)) {
                log.error("{}AliPay Notify parameter total_amount is empty. total_fee={}", logPrefix, total_amount);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }

            // 查询payOrder记录
            String payOrderId = out_trade_no;
            PayOrder payOrder = payOrderService.findPayOrderByOrderId(payOrderId);
            if (null == payOrder) {
                log.error("{}Can't found payOrder form db. payOrderId={}, ", logPrefix, payOrderId);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }

            // 查询payChannel记录
            String mchId = payOrder.getMch_id();
            String channelId = payOrder.getChannelId();
            PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
            if (null == payChannel) {
                log.error("{}Can't found payChannel form db. mchId={} channelId={}, ", logPrefix, payOrderId, mchId, channelId);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }

            boolean verify_result = false;
            try {
                verify_result = AlipaySignature.rsaCheckV1(params, AlipayUtil.init(payChannel.getParam()).getAlipayPublicKey(), AlipayConfig.CHARSET, "RSA2");
            } catch (AlipayApiException e) {
                log.error("{}AlipaySignature.rsaCheckV1 error:" + e, logPrefix);
            }

            // 验证签名
            if (!verify_result) return PayConstant.RETURN_ALIPAY_VALUE_FAIL;

            // 核对金额
            long aliPayAmt = new BigDecimal(total_amount).movePointRight(2).longValue();
            long dbPayAmt = payOrder.getAmount().longValue();
            if (dbPayAmt != aliPayAmt) {
                log.error("{}db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}", logPrefix, aliPayAmt, payOrderId);
                return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
            }

            // 处理订单
            int payStatus = payOrder.getStatus(); // 0：订单生成，1：支付中，-1：支付失败，2：支付成功，3：业务处理完成，-2：订单过期
            if (payStatus == PayConstant.PAY_STATUS_COMPLETE) { // 处理完成
                log.info("====== 订单已经完成直接返回 ======");
                return PayConstant.RETURN_ALIPAY_VALUE_SUCCESS;
            }

            if (payStatus != PayConstant.PAY_STATUS_SUCCESS) {
                boolean updatePayOrderRows = payOrderService.updatePayOrderStatus4Success(payOrder.getPayOrderId());
                if (!updatePayOrderRows) {
                    log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_FAILED);
                    return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
                }
                log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
            }
            // 业务系统后端通知
            this.notifyService.notifyPayOrder(payOrder);
            log.info("====== 完成接收支付宝支付回调通知 ======");
            return PayConstant.RETURN_ALIPAY_VALUE_SUCCESS;
        } catch (Exception e) {
            log.error("支付宝回调结果异常,异常原因" + e);
            return PayConstant.RETURN_ALIPAY_VALUE_FAIL;
        }
    }
}

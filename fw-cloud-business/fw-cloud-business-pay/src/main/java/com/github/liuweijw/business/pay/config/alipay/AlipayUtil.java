package com.github.liuweijw.business.pay.config.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.liuweijw.commons.utils.PublicHelper;
import org.springframework.util.Assert;

public class AlipayUtil {

    /**
     * 初始化支付宝配置
     */
    public static AlipayConfig init(String configParam) {
        return init(configParam, null);
    }

    public static AlipayConfig init(String configParam, String returnUrl) {
        Assert.notNull(configParam, "init alipay config error");
        AlipayConfig config = new AlipayConfig();
        JSONObject paramObj = JSON.parseObject(configParam);
        config.setAppId(paramObj.getString("appid"));
        config.setRsaPrivateKey(paramObj.getString("private_key"));
        config.setAlipayPublicKey(paramObj.getString("alipay_public_key"));
        config.setIsSandbox(paramObj.getShortValue("isSandbox"));
        if (!PublicHelper.isEmpty(returnUrl)) {
            config.setReturnUrl(returnUrl);
        } else {
            config.setReturnUrl(paramObj.getString("returnUrl"));
        }
        if (config.getIsSandbox() == 1) config.setUrl("https://openapi.alipaydev.com/gateway.do");
        return config;
    }

}

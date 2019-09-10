package com.github.liuweijw.business.pay.config.alipay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlipayConfig {

    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // RSA2
    public static String SIGNTYPE = "RSA2";
    // 支付宝公钥
    public String alipayPublicKey;
    // 商户appid
    private String appId;
    // 私钥 pkcs8格式的
    private String rsaPrivateKey;
    // 请求网关地址
    private String url = "https://openapi.alipay.com/gateway.do";
    // 是否沙箱环境,1:沙箱,0:正式环境
    private Short isSandbox = 0;

    private String returnUrl;

}

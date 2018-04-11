package com.github.liuweijw.business.pay.config.wechat;

import java.io.File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.config.WxPayConfig;

/**
 * 微信支付工具
 * 
 * @author liuweijw
 */
public class WxPayUtil {

	/**
	 * 获取微信支付配置
	 */
	public static WxPayConfig getWxPayConfig(String configParam, String tradeType,
			String certRootPath, String notifyUrl) {
		WxPayConfig wxPayConfig = new WxPayConfig();
		JSONObject paramObj = JSON.parseObject(configParam);
		wxPayConfig.setMchId(paramObj.getString("mchId"));
		wxPayConfig.setAppId(paramObj.getString("appId"));
		wxPayConfig.setKeyPath(certRootPath + File.separator + paramObj.getString("certLocalPath"));
		wxPayConfig.setMchKey(paramObj.getString("key"));
		wxPayConfig.setNotifyUrl(notifyUrl);
		wxPayConfig.setTradeType(tradeType);
		return wxPayConfig;
	}

	/**
	 * 获取微信支付配置
	 */
	public static WxPayConfig getWxPayConfig(String configParam) {
		WxPayConfig wxPayConfig = new WxPayConfig();
		JSONObject paramObj = JSON.parseObject(configParam);
		wxPayConfig.setMchId(paramObj.getString("mchId"));
		wxPayConfig.setAppId(paramObj.getString("appId"));
		wxPayConfig.setMchKey(paramObj.getString("key"));
		return wxPayConfig;
	}

}

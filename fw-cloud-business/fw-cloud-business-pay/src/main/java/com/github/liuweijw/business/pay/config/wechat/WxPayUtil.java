package com.github.liuweijw.business.pay.config.wechat;

import java.io.File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.liuweijw.commons.utils.PublicHelper;

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
		wxPayConfig.setMchKey(paramObj.getString("key"));
		if (!PublicHelper.isEmpty(certRootPath)) wxPayConfig.setKeyPath(certRootPath + File.separator + paramObj.getString("certLocalPath"));
		if (!PublicHelper.isEmpty(notifyUrl)) wxPayConfig.setNotifyUrl(notifyUrl);
		if (!PublicHelper.isEmpty(tradeType)) wxPayConfig.setTradeType(tradeType);
		return wxPayConfig;
	}

	/**
	 * 获取微信支付配置
	 */
	public static WxPayConfig getWxPayConfig(String configParam, String certRootPath) {
		return getWxPayConfig(configParam, null, certRootPath, null);
	}

	/**
	 * 获取微信支付配置
	 */
	public static WxPayConfig getWxPayConfig(String configParam) {
		return getWxPayConfig(configParam, null);
	}

}

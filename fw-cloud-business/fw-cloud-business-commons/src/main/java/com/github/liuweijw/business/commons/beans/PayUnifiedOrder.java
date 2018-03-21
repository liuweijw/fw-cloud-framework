package com.github.liuweijw.business.commons.beans;

import java.io.Serializable;

/**
 * 统一下单服务Bean
 * 
 * @author liuweijw
 *
 */
public class PayUnifiedOrder implements Serializable {

	private static final long serialVersionUID = -4417796108118232752L;

	/**
	 * 商户id 必填
	 */
	private String mchId;
	
	/**
	 * 商户订单号 必填
	 */
	private String mchOrderNo;
	
	/**
	 * 支付渠道ID, WX_NATIVE,ALIPAY_WAP 必填
	 */
	private String channelId;
	
	/**
	 * 支付金额,单位分 必填
	 */
	private Long amount;
	
	/**
	 * 币种, cny-人民币 必填
	 */
	private String currency = "cny";
	
	/**
	 * 用户地址,IP或手机号 必填
	 */
	private String ip;
	
	/**
	 *  设备 "WEB"  必填
	 */
	private String device;
	
	/**
	 * 简介  必填
	 */
	private String subject;
	
	/**
	 * 介绍  必填
	 */
	private String body;
	
	/**
	 * 业务回调URL  必填
	 */
	private String notifyUrl;
	
	/**
	 * 扩展参数1  选填
	 */
	private String param1;
	
	/**
	 * 扩展参数2  选填
	 */
	private String param2;
	
	/**
	 * 附加参数 JSON "{\"productId\":\"xxx\",\"openId\":\"xxx\"}"
	 */
	private String extra;
	
	/**
	 * 上述字段   签名
	 */
	private String sign;
	
	@Override
	public String toString() {
		return "PayUnifiedOrder [mchId=" + mchId + ", mchOrderNo=" + mchOrderNo
				+ ", channelId=" + channelId + ", amount=" + amount
				+ ", currency=" + currency + ", ip=" + ip + ", device="
				+ device + ", subject=" + subject + ", body=" + body
				+ ", notifyUrl=" + notifyUrl + ", param1=" + param1
				+ ", param2=" + param2 + ", extra=" + extra + ", sign=" + sign
				+ "]";
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchOrderNo() {
		return mchOrderNo;
	}

	public void setMchOrderNo(String mchOrderNo) {
		this.mchOrderNo = mchOrderNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}

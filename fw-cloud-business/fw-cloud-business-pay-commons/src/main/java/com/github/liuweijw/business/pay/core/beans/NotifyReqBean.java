package com.github.liuweijw.business.pay.core.beans;

import java.io.Serializable;

/**
 * 支付通知请求Bean
 * 
 * @author liuweijw
 *
 */
public class NotifyReqBean implements Serializable {

	private static final long serialVersionUID = -7650462120420037976L;

	private String payOrderId;
	
	private String mchId;
	
	private String mchOrderNo;
	
	private String channelId;
	
	private Long amount;
	
	private String currency;
	
	private Integer status;
	
	private String ip;
	
	private String device;
	
	private String subject;
	
	private String channelOrderNo;
	
	private String param1;
	
	private String param2;
	
	private Long paySuccTime;
	
	private String backType;
	
	private String sign;

	@Override
	public String toString() {
		return "NotifyReqBean [payOrderId=" + payOrderId + ", mchId=" + mchId
				+ ", mchOrderNo=" + mchOrderNo + ", channelId=" + channelId
				+ ", amount=" + amount + ", currency=" + currency + ", status="
				+ status + ", ip=" + ip + ", device=" + device + ", subject="
				+ subject + ", channelOrderNo=" + channelOrderNo + ", param1="
				+ param1 + ", param2=" + param2 + ", paySuccTime="
				+ paySuccTime + ", backType=" + backType + ", sign=" + sign
				+ "]";
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
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

	public Long getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Long paySuccTime) {
		this.paySuccTime = paySuccTime;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}

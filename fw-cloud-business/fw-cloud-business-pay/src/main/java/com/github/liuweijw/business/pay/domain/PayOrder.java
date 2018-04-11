package com.github.liuweijw.business.pay.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 支付订单信息表
 * 
 * @author liuweijw
 */
@Entity
@Table(name = PayOrder.TABLE_NAME)
public class PayOrder implements Serializable {

	private static final long	serialVersionUID	= 4833637614310492703L;

	public static final String	TABLE_NAME			= "t_pay_order";

	/**
	 * 渠道ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 支付订单号
	 */
	@Column(name = "pay_order_id")
	private String				payOrderId;

	/**
	 * 商户ID
	 */
	@Column(name = "mch_id")
	private String				mch_id;

	/**
	 * 商户订单号
	 */
	@Column(name = "mch_order_no")
	private String				mchOrderNo;

	/**
	 * 渠道ID
	 */
	@Column(name = "channel_id")
	private String				channelId;

	/**
	 * 支付金额,单位分
	 */
	@Column(name = "amount")
	private Long				amount;

	/**
	 * 三位货币代码,人民币:cny
	 */
	@Column(name = "currency")
	private String				currency;

	/**
	 * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
	 */
	@Column(name = "status")
	private Integer				status				= 0;

	/**
	 * 客户端IP
	 */
	@Column(name = "ip")
	private String				ip;

	/**
	 * 设备
	 */
	@Column(name = "device")
	private String				device;

	/**
	 * 商品标题
	 */
	@Column(name = "subject")
	private String				subject;

	/**
	 * 商品描述信息
	 */
	@Column(name = "body")
	private String				body;

	/**
	 * 特定渠道发起时额外参数
	 */
	@Column(name = "extra")
	private String				extra;

	/**
	 * 渠道商户ID
	 */
	@Column(name = "channel_mch_id")
	private String				channelMchId;

	/**
	 * 渠道订单号
	 */
	@Column(name = "channel_order_no")
	private String				channelOrderNo;

	/**
	 * 渠道支付错误码
	 */
	@Column(name = "errcode")
	private String				errCode;

	/**
	 * 渠道支付错误描述
	 */
	@Column(name = "errmsg")
	private String				errMsg;

	/**
	 * 扩展参数1
	 */
	@Column(name = "param1")
	private String				param1;

	/**
	 * 扩展参数2
	 */
	@Column(name = "param2")
	private String				param2;

	/**
	 * 通知地址
	 */
	@Column(name = "notify_url")
	private String				notifyUrl;

	/**
	 * 通知次数
	 */
	@Column(name = "notify_count")
	private Integer				notifyCount			= 0;

	/**
	 * 最后一次通知时间
	 */
	@Column(name = "last_notify_time")
	private Long				lastNotifyTime;

	/**
	 * 订单失效时间
	 */
	@Column(name = "expire_time")
	private Long				expireTime;

	/**
	 * 订单支付成功时间
	 */
	@Column(name = "pay_succ_time")
	private Long				paySuccTime;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date				createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date				updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getChannelMchId() {
		return channelMchId;
	}

	public void setChannelMchId(String channelMchId) {
		this.channelMchId = channelMchId;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}

	public Long getLastNotifyTime() {
		return lastNotifyTime;
	}

	public void setLastNotifyTime(Long lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Long getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Long paySuccTime) {
		this.paySuccTime = paySuccTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}

package com.github.liuweijw.business.pay.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退款信息表
 * 
 * @author liuweijw
 *
 */
@Entity
@Table(name = RefundOrder.TABLE_NAME)
public class RefundOrder implements Serializable {

	private static final long serialVersionUID = -2274499535484106305L;

	public static final String TABLE_NAME = "t_pay_refund_order";

	/**
     * 渠道ID
     */
	@Id
	@GeneratedValue
	@Column(name = "id")
    private Long id;
	
	/**
     * 退款订单号
     */
	@Column(name = "refund_order_id")
    private String refundOrderId;

    /**
     * 支付订单号
     */
	@Column(name = "pay_order_id")
    private String payOrderId;

    /**
     * 渠道支付单号
     */
	@Column(name = "channel_payorderno")
    private String channelPayOrderNo;

    /**
     * 商户ID
     */
	@Column(name = "mch_id")
    private String mchId;

    /**
     * 商户退款单号
     */
	@Column(name = "mch_refund_no")
    private String mchRefundNo;

    /**
     * 渠道ID
     */
	@Column(name = "channel_id")
    private String channelId;

    /**
     * 支付金额,单位分
     */
	@Column(name = "pay_amount")
    private Long payAmount;

    /**
     * 退款金额,单位分
     */
	@Column(name = "refund_amount")
    private Long refundAmount;

    /**
     * 三位货币代码,人民币:cny
     */
	@Column(name = "currency")
    private String currency;

    /**
     * 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成
     */
	@Column(name = "status")
    private Integer status;

    /**
     * 退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
     */
	@Column(name = "result")
    private Integer result;

    /**
     * 客户端IP
     */
	@Column(name = "ip")
    private String ip;

    /**
     * 设备
     */
	@Column(name = "device")
    private String device;

    /**
     * 备注
     */
	@Column(name = "remark")
    private String remark;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
	@Column(name = "channel_user")
    private String channelUser;

    /**
     * 用户姓名
     */
	@Column(name = "user_name")
    private String userName;

    /**
     * 渠道商户ID
     */
	@Column(name = "channel_mch_id")
    private String channelMchId;

    /**
     * 渠道订单号
     */
	@Column(name = "channel_order_no")
    private String channelOrderNo;

    /**
     * 渠道错误码
     */
	@Column(name = "channel_errcode")
    private String channelErrCode;

    /**
     * 渠道错误描述
     */
	@Column(name = "channel_errmsg")
    private String channelErrMsg;

    /**
     * 特定渠道发起时额外参数
     */
	@Column(name = "extra")
    private String extra;

    /**
     * 通知地址
     */
	@Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 扩展参数1
     */
	@Column(name = "param1")
    private String param1;

    /**
     * 扩展参数2
     */
	@Column(name = "param2")
    private String param2;

    /**
     * 订单失效时间
     */
	@Column(name = "expire_time")
    private Date expireTime;

    /**
     * 订单退款成功时间
     */
	@Column(name = "refund_succ_time")
    private Date refundSuccTime;

	/**
     * 创建时间
     */
	@Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
	@Column(name = "update_time")
    private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getChannelPayOrderNo() {
		return channelPayOrderNo;
	}

	public void setChannelPayOrderNo(String channelPayOrderNo) {
		this.channelPayOrderNo = channelPayOrderNo;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchRefundNo() {
		return mchRefundNo;
	}

	public void setMchRefundNo(String mchRefundNo) {
		this.mchRefundNo = mchRefundNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChannelUser() {
		return channelUser;
	}

	public void setChannelUser(String channelUser) {
		this.channelUser = channelUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getChannelErrCode() {
		return channelErrCode;
	}

	public void setChannelErrCode(String channelErrCode) {
		this.channelErrCode = channelErrCode;
	}

	public String getChannelErrMsg() {
		return channelErrMsg;
	}

	public void setChannelErrMsg(String channelErrMsg) {
		this.channelErrMsg = channelErrMsg;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
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

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getRefundSuccTime() {
		return refundSuccTime;
	}

	public void setRefundSuccTime(Date refundSuccTime) {
		this.refundSuccTime = refundSuccTime;
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

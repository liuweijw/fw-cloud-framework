package com.github.liuweijw.business.pay.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付订单信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
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

}

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
 * 支付通知
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = PayMchNotify.TABLE_NAME)
public class PayMchNotify implements Serializable {

	private static final long	serialVersionUID	= 6254053494216384709L;

	public static final String	TABLE_NAME			= "t_pay_mch_notify";

	/**
	 * 渠道ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 订单ID
	 */
	@Column(name = "order_id")
	private String				orderId;

	/**
	 * 商户ID
	 */
	@Column(name = "mch_id")
	private String				mchId;

	/**
	 * 商户订单号
	 */
	@Column(name = "mch_order_no")
	private String				mchOrderNo;

	/**
	 * 订单类型:1-支付,2-转账,3-退款
	 */
	@Column(name = "order_type")
	private String				orderType;

	/**
	 * 通知地址
	 */
	@Column(name = "notify_url")
	private String				notifyUrl;

	/**
	 * 通知次数
	 */
	@Column(name = "notify_count")
	private Integer				notifyCount;

	/**
	 * 通知响应结果
	 */
	@Column(name = "result")
	private String				result;

	/**
	 * 通知状态,1-通知中,2-通知成功,3-通知失败
	 */
	@Column(name = "status")
	private Integer				status;

	/**
	 * 最后一次通知时间
	 */
	@Column(name = "last_notify_time")
	private Date				lastNotifyTime;

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

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
 * 苹果支付凭据表
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = IapReceipt.TABLE_NAME)
public class IapReceipt implements Serializable {

	private static final long	serialVersionUID	= 3451080875462097791L;

	public static final String	TABLE_NAME			= "t_pay_iap_receipt";

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
	private String				mchId;

	/**
	 * IAP业务号
	 */
	@Column(name = "transaction_id")
	private String				transactionId;

	/**
	 * 处理状态:0-未处理,1-处理成功,-1-处理失败
	 */
	@Column(name = "status")
	private Integer				status;

	/**
	 * 处理次数
	 */
	@Column(name = "handle_count")
	private Integer				handleCount;

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

	/**
	 * 渠道ID
	 */
	@Column(name = "receipt_data")
	private String				receiptData;

}

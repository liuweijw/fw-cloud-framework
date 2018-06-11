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
 * 支付渠道信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = PayChannel.TABLE_NAME)
public class PayChannel implements Serializable {

	private static final long	serialVersionUID	= -9030463424019489840L;

	public static final String	TABLE_NAME			= "t_pay_channel";

	/**
	 * 渠道ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 渠道ID
	 */
	@Column(name = "channel_id")
	private String				channelId;

	/**
	 * 渠道名称,如:alipay,wechat
	 */
	@Column(name = "channel_name")
	private String				channelName;

	/**
	 * 渠道商户ID
	 */
	@Column(name = "channel_mch_id")
	private String				channelMchId;

	/**
	 * 商户ID
	 */
	@Column(name = "mch_id")
	private String				mchId;

	/**
	 * 渠道状态,0-停止使用,1-使用中
	 */
	@Column(name = "statu")
	private Integer				statu;

	/**
	 * 配置参数,json字符串
	 */
	@Column(name = "param")
	private String				param;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String				remark;

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

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
 * 转账信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = RefundOrder.TABLE_NAME)
public class TransOrder implements Serializable {

	private static final long	serialVersionUID	= -3381668479926407764L;

	public static final String	TABLE_NAME			= "t_pay_trans_order";

	/**
	 * 渠道ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 转账订单号
	 */
	@Column(name = "trans_order_id")
	private String				transOrderId;

	/**
	 * 商户ID
	 */
	@Column(name = "mch_id")
	private String				mchId;

	/**
	 * 商户转账单号
	 */
	@Column(name = "mch_trans_no")
	private String				mchTransNo;

	/**
	 * 渠道ID
	 */
	@Column(name = "channel_id")
	private String				channelId;

	/**
	 * 转账金额,单位分
	 */
	@Column(name = "amount")
	private Long				amount;

	/**
	 * 三位货币代码,人民币:cny
	 */
	@Column(name = "currency")
	private String				currency;

	/**
	 * 转账状态:0-订单生成,1-转账中,2-转账成功,3-转账失败,4-业务处理完成
	 */
	@Column(name = "status")
	private Integer				status;

	/**
	 * 转账结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败
	 */
	@Column(name = "result")
	private Integer				result;

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
	 * 备注
	 */
	@Column(name = "remark")
	private String				remark;

	/**
	 * 渠道用户标识,如微信openId,支付宝账号
	 */
	@Column(name = "channel_user")
	private String				channelUser;

	/**
	 * 用户姓名
	 */
	@Column(name = "user_name")
	private String				userName;

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
	 * 渠道错误码
	 */
	@Column(name = "channel_errcode")
	private String				channelErrCode;

	/**
	 * 渠道错误描述
	 */
	@Column(name = "channel_errmsg")
	private String				channelErrMsg;

	/**
	 * 特定渠道发起时额外参数
	 */
	@Column(name = "extra")
	private String				extra;

	/**
	 * 通知地址
	 */
	@Column(name = "notify_url")
	private String				notifyUrl;

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
	 * 订单失效时间
	 */
	@Column(name = "expire_time")
	private Date				expireTime;

	/**
	 * 订单转账成功时间
	 */
	@Column(name = "trans_succ_time")
	private Date				transSuccTime;

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

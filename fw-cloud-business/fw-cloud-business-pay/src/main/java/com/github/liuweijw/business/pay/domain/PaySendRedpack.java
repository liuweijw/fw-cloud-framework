package com.github.liuweijw.business.pay.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.binarywang.wxpay.config.WxPayConfig;

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
@Table(name = PaySendRedpack.TABLE_NAME)
public class PaySendRedpack implements Serializable {

	private static final long		serialVersionUID	= 8175314774393568447L;

	public static final String		TABLE_NAME			= "t_pay_sendredpack";

	/**
	 * 渠道ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long					id;

	/**
	 * 发送流水
	 */
	@Column(name = "send_order_id")
	private String					sendOrderId;

	/**
	 * 红包类型 0 普通红包 1 裂变红包 必填
	 */
	@Column(name = "red_pack_type")
	private Integer					redPackType;

	/**
	 * 商户id 必填
	 */
	@Column(name = "mch_id")
	private String					mchId;

	/**
	 * 商户订单号 必填
	 */
	@Column(name = "mch_order_no")
	private String					mchOrderNo;

	/**
	 * 红包默认采用 "WX_JSAPI"
	 */
	@Column(name = "channel_id")
	private String					channelId			= "WX_JSAPI";

	/**
	 * 用户openid 必填
	 */
	@Column(name = "open_id")
	private String					openId;

	/**
	 * 红包发放总金额，即一组红包金额总和，包括分享者的红包和裂变的红包，单位分 必填
	 */
	@Column(name = "total_amount")
	private Integer					totalAmount;

	/**
	 * 红包发放总人数，即总共有多少人可以领到该组红包（包括分享者） 必填
	 */
	@Column(name = "total_num")
	private Integer					totalNum;

	/**
	 * 红包金额设置方式 必填
	 * ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
	 */
	@Column(name = "amt_type")
	private String					amtType;

	/**
	 * 红包祝福语 必填
	 */
	@Column(name = "wishing")
	private String					wishing;

	/**
	 * Ip地址 必填
	 */
	@Column(name = "ip")
	private String					ip;

	/**
	 * 活动名称 必填
	 */
	@Column(name = "act_name")
	private String					actName;

	/**
	 * 备注 必填
	 * ex: 猜越多得越多，快来抢！
	 */
	@Column(name = "remark")
	private String					remark;

	/**
	 * 非必填
	 * PRODUCT_1:商品促销
	 * PRODUCT_2:抽奖
	 * PRODUCT_3:虚拟物品兑奖
	 * PRODUCT_4:企业内部福利
	 * PRODUCT_5:渠道分润
	 * PRODUCT_6:保险回馈
	 * PRODUCT_7:彩票派奖
	 * PRODUCT_8:税务刮奖
	 */
	@Column(name = "scene_id")
	private String					sceneId;

	/**
	 * 活动信息 非必填
	 * posttime:用户操作的时间戳
	 * mobile:业务系统账号的手机号，国家代码-手机号。不需要+号
	 * deviceid :mac 地址或者设备唯一标识
	 * clientversion :用户操作的客户端版本
	 * 把值为非空的信息用key=value进行拼接，再进行urlencode
	 * urlencode(posttime=xx& mobile =xx&deviceid=xx)
	 */
	@Column(name = "risk_info")
	private String					riskInfo;

	/**
	 * 资金授权商户号 非必填
	 * 服务商替特约商户发放时使用
	 */
	@Column(name = "consume_mch_id")
	private String					consumeMchId;

	/**
	 * 返回状态码 SUCCESS/FAIL
	 */
	@Column(name = "return_code")
	private String					returnCode;

	/**
	 * 返回信息，如非空，为错误原因
	 */
	@Column(name = "return_msg")
	private String					returnMsg;

	/**
	 * 付款金额 付款金额，单位分 微信端返回
	 */
	@Column(name = "wx_total_amount")
	private Integer					wxTotalAmount;

	/**
	 * 微信单号
	 */
	@Column(name = "send_listid")
	private String					sendListid;

	/**
	 * 发送时间
	 */
	@Column(name = "send_time")
	private String					sendTime;

	/**
	 * 业务返回状态码 SUCCESS/FAIL
	 */
	@Column(name = "result_code")
	private String					resultCode;

	/**
	 * 错误代码
	 */
	@Column(name = "err_code")
	private String					errCode;

	/**
	 * 错误代码描述
	 */
	@Column(name = "err_code_des")
	private String					errCodeDes;

	private transient WxPayConfig	wxPayConfig;

	private transient String		resKey;

}

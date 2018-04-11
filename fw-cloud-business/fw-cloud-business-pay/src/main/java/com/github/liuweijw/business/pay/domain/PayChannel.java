package com.github.liuweijw.business.pay.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 支付渠道信息表
 * 
 * @author liuweijw
 */
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelMchId() {
		return channelMchId;
	}

	public void setChannelMchId(String channelMchId) {
		this.channelMchId = channelMchId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

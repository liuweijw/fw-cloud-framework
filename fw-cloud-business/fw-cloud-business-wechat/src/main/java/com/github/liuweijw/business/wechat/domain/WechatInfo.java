package com.github.liuweijw.business.wechat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 多公众号信息表
 * 
 * @author liuweijw
 */
@Entity
@Table(name = WechatInfo.TABLE_NAME)
public class WechatInfo implements Serializable {

	private static final long	serialVersionUID	= 9176316916106439234L;

	public static final String	TABLE_NAME			= "t_wechat_info";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 公众号ID，系统自定义
	 */
	@Column(name = "wechat_id")
	private String				wechatId;

	/**
	 * 名称
	 */
	@Column(name = "wechat_name")
	private String				wechatName;

	/**
	 * 请求私钥
	 */
	@Column(name = "req_key")
	private String				reqKey;

	/**
	 * 响应私钥
	 */
	@Column(name = "res_key")
	private String				resKey;

	/**
	 * 公众号状态,0-停止使用,1-使用中
	 */
	@Column(name = "statu")
	private Integer				statu				= 0;

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

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getReqKey() {
		return reqKey;
	}

	public void setReqKey(String reqKey) {
		this.reqKey = reqKey;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
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

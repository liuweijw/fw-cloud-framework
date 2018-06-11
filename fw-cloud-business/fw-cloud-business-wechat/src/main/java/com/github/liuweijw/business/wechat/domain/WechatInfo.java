package com.github.liuweijw.business.wechat.domain;

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
 * 多公众号信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
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

}

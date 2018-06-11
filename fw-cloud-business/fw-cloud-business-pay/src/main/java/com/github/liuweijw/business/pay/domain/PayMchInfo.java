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
 * 商户信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = PayMchInfo.TABLE_NAME)
public class PayMchInfo implements Serializable {

	private static final long	serialVersionUID	= 6226786548520958050L;

	public static final String	TABLE_NAME			= "t_pay_mch_info";

	/**
	 * 菜单ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	/**
	 * 商户ID
	 */
	@Column(name = "mch_id")
	private String				mchId;

	/**
	 * 名称
	 */
	@Column(name = "mch_name")
	private String				mchName;

	/**
	 * 类型
	 */
	@Column(name = "type")
	private String				type;

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
	 * 商户状态,0-停止使用,1-使用中
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

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
 * 微信用户信息保存
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = AuthInfo.TABLE_NAME)
public class AuthInfo implements Serializable {

	private static final long	serialVersionUID	= 49262005715559358L;

	public static final String	TABLE_NAME			= "t_wechat_auth_info";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	@Column(name = "open_id", length = 64)
	private String				openId;

	@Column(name = "wechat_id", length = 32)
	private String				wechatId;

	@Column(name = "nick_name", length = 255)
	private String				nickName;

	@Column(name = "sex_desc", length = 20)
	private String				sexDesc;

	@Column(name = "sex_int", length = 11)
	private Integer				sex;

	@Column(name = "language", length = 64)
	private String				language;

	@Column(name = "city", length = 64)
	private String				city;

	@Column(name = "province", length = 64)
	private String				province;

	@Column(name = "country", length = 64)
	private String				country;

	@Column(name = "head_imgurl", length = 255)
	private String				headImgUrl;

	@Column(name = "remark", length = 255)
	private String				remark;

	@Column(name = "refresh_token", length = 255)
	private String				refreshToken;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date				createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date				updateTime			= new Date();

}

package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单位管理
 *
 * @author liuweijw
 */
@Data
@Entity
@ApiModel(description = "单位管理")
@Table(name = Company.TABLE_NAME)
public class Company implements Serializable {

	private static final long	serialVersionUID	= -2426291287870643361L;

	public static final String	TABLE_NAME			= "t_sys_company";

	@Id
	@Column(name = "id")
	@ApiModelProperty("自增id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				id;

	@Column(name = "code")
	@ApiModelProperty("单位编码")
	private String				code;

	@Column(name = "name")
	@ApiModelProperty("单位名称 默认为会智公司关联")
	private String				name;

	@Column(name = "statu")
	@ApiModelProperty("是否生效， 0 默认生效")
	private Integer				statu				= 0;

	@Column(name = "ctime")
	@ApiModelProperty("创建时间")
	private Date				ctime;

}

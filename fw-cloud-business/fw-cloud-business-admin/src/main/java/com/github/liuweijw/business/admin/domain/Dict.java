package com.github.liuweijw.business.admin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典信息
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = Dict.TABLE_NAME)
@ApiModel(description = "字典信息")
public class Dict implements Serializable {

	private static final long	serialVersionUID	= 6336455104080722637L;

	public static final String	TABLE_NAME			= "t_sys_dict";

	/**
	 * 部门id
	 */
	@Id
	@GeneratedValue
	@ApiModelProperty("部门id")
	@Column(name = "id")
	private Integer				id;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	@Column(name = "type")
	private String				type;

	/**
	 * 数据值
	 */
	@ApiModelProperty("字典值")
	@Column(name = "value")
	private String				value;

	/**
	 * 标签名
	 */
	@ApiModelProperty("标签名")
	@Column(name = "label")
	private String				label;

	/**
	 * 0--正常 1--删除
	 */
	@ApiModelProperty("0--正常 1--删除")
	@Column(name = "statu")
	private Integer				statu				= 0;

}

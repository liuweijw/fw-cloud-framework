package com.github.liuweijw.system.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "用户角色")
public class AuthRole implements Serializable {

	private static final long	serialVersionUID	= -213874145064828983L;

	/**
	 * 角色ID
	 */
	@ApiModelProperty("用户角色ID")
	private Integer				roleId;

	/**
	 * 角色名称
	 */
	@ApiModelProperty("用户角色名称")
	private String				roleName;

	/**
	 * 角色编码，唯一
	 */
	@ApiModelProperty("用户角色编码")
	private String				roleCode;

	/**
	 * 描述
	 */
	@ApiModelProperty("用户角色描述")
	private String				roleDesc;

	/**
	 * 0-正常，1-删除
	 */
	@ApiModelProperty("0-正常，1-删除")
	private Integer				statu;

}

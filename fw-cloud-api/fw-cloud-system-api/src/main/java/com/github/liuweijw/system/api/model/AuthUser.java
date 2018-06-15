package com.github.liuweijw.system.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "用户信息")
public class AuthUser implements Serializable {

	private static final long	serialVersionUID	= 5350461830095965990L;

	/**
	 * 用户名Id
	 */
	@ApiModelProperty("用户userId")
	private Integer				userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	private String				username;

	/**
	 * 密码
	 */
	@ApiModelProperty("用户名密码")
	private String				password;

	/**
	 * 0-正常，1-删除
	 */
	@ApiModelProperty("0-正常，1-删除")
	private Integer				statu				= 0;

	/**
	 * 头像
	 */
	@ApiModelProperty("用户头像")
	private String				picUrl;

	/**
	 * 角色列表
	 */
	@ApiModelProperty("用户角色列表")
	private List<AuthRole>		roleList			= new ArrayList<AuthRole>();

}

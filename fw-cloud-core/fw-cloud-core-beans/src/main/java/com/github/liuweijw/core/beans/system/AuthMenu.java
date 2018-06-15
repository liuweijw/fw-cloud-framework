package com.github.liuweijw.core.beans.system;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthMenu implements Serializable {

	private static final long	serialVersionUID	= 4566420419542436770L;

	/**
	 * 菜单id
	 */
	private Integer				menuId;

	/**
	 * 菜单名称
	 */
	private String				menuName;

	/**
	 * 请求路径
	 */
	private String				path;

	/**
	 * 请求链接
	 */
	private String				url;

	/**
	 * 父菜单ID
	 */
	private Integer				pid;

	/**
	 * 0-正常，1-删除
	 */
	private Integer				statu				= 0;

}

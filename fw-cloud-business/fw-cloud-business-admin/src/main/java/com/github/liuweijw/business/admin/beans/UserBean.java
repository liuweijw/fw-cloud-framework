package com.github.liuweijw.business.admin.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.github.liuweijw.business.admin.domain.User;

/**
 * @author liuweijw
 */
@Setter
@Getter
public class UserBean implements Serializable {

	private static final long	serialVersionUID	= 4100476652382025202L;

	/**
	 * 用户基本信息
	 */
	private User				user;

	/**
	 * 权限标识集合
	 */
	private String[]			permissions;

	/**
	 * 角色集合
	 */
	private String[]			roles;

}

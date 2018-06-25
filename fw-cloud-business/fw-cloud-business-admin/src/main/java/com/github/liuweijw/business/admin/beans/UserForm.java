package com.github.liuweijw.business.admin.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户请求表单
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class UserForm {

	private String	username;

	private String	password;

	private String	newpassword;

	private Integer	statu	= 0;

	private Integer	roleId;

	private Integer	userId;

	private Integer	deptId;

	private String	mobile;

}

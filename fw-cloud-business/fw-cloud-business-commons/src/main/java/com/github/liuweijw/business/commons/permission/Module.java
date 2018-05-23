package com.github.liuweijw.business.commons.permission;

/**
 * 功能模块权限
 * 
 * @author liuweijw
 */
public interface Module {

	/**
	 * 用户模块
	 */
	String	USER	= "user_";

	/**
	 * 系统模块
	 */
	String	MENU	= "menu_";

	/**
	 * 角色模块
	 */
	String	ROLE	= "role_";

	/**
	 * 部门模块
	 */
	String	DEPT	= "dept_";

	/**
	 * 字典模块
	 */
	String	DICT	= "dict_";

	/**
	 * 日志模块
	 */
	String	LOG		= "logs_";

	/**
	 * 验证码模块
	 */
	String	CODE	= "code_";

	/**
	 * 仅仅系统内部模块间调用使用
	 */
	String	API		= "api_";

}

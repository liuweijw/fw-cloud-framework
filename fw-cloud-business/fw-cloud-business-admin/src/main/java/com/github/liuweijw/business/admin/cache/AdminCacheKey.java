package com.github.liuweijw.business.admin.cache;

/**
 * 各种缓存Key定义
 * 
 * @author liuweijw
 *
 */
public interface AdminCacheKey {

	String USER_INFO = "user_info_";
	String USER_INFO_KEY_USERNAME = "#username";
	
	String USER_INFO_MOBILE = "user_info_mobile";
	String USER_INFO_MOBILE_KEY_USERNAME = "#mobile";
	
	String USER_INFO_USERID = "user_info_userId";
	String USER_INFO_USERID_KEY_USERID = "#userId";
	
	String MENU_INFO = "menu_info_";
	String MENU_INFO_KEY_ROLECODE = "#roleCode + '_menu'";
	
	String PERMISSION_INFO = "permission_info_";
	String PERMISSION_INFO_KEY_ROLECODE = "#roleCode";
	
	String ROLE_INFO_LIST = "role_info_list";
}

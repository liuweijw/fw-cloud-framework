package com.github.liuweijw.admin.service;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.core.beans.system.AuthUser;

public interface UserService {

	public AuthUser findUserByUsername(String username);
	
	public AuthUser findUserByMobile(String mobile);
	
	public void saveImageCode(String randomStr, String text);

	public UserBean findUserInfo(AuthUser user);

	public AuthUser selectById(Integer id);
	
}

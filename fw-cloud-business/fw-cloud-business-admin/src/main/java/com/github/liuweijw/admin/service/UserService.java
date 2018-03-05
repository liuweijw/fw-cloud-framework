package com.github.liuweijw.admin.service;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.domain.User;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
import com.github.liuweijw.core.beans.system.AuthUser;

public interface UserService {

	public AuthUser findUserByUsername(String username);
	
	public AuthUser findUserByMobile(String mobile);
	
	public void saveImageCode(String randomStr, String text);

	public UserBean findUserInfo(AuthUser user);

	public AuthUser findByUserId(Integer userId);

	public PageBean<User> findAll(PageParams pageParams, User user);

	public Boolean delByUserId(Integer userId);
	
}

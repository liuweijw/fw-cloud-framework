package com.github.liuweijw.business.admin.service;

import com.github.liuweijw.business.admin.beans.UserBean;
import com.github.liuweijw.business.admin.beans.UserForm;
import com.github.liuweijw.business.admin.domain.User;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.system.api.model.AuthUser;

public interface UserService {

	public AuthUser findUserByUsername(String username);

	public User findUserByUsername(String username, boolean isLoadRole);

	public AuthUser findUserByMobile(String mobile);

	public void saveImageCode(String randomStr, String text);

	public UserBean findUserInfo(AuthUser user);

	public AuthUser findByUserId(String userId);

	public PageBean<User> findAll(PageParams pageParams, User user);

	public Boolean delByUserId(Integer userId);

	public boolean addUserAndRoleDept(UserForm userForm);

	public boolean updateUserAndRoleDept(UserForm userForm);

	public boolean updateUser(User user);

}

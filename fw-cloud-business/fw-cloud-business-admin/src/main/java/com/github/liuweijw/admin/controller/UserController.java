package com.github.liuweijw.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.service.UserService;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.web.BaseController;
import com.github.liuweijw.core.utils.R;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	/**
     * 获取当前用户信息（角色、权限）
     */
    @GetMapping("/info")
    public R<UserBean> user(AuthUser user) {
        return new R<UserBean>().data(userService.findUserInfo(user));
    }

    /**
     * 通过ID查询当前用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public AuthUser user(@PathVariable Integer id) {
        return userService.selectById(id);
    }
	
	/**
     * 通过用户名查询用户及其角色信息
     */
    @GetMapping("/findUserByUsername/{username}")
    public AuthUser findUserByUsername(@PathVariable String username) {
    	return userService.findUserByUsername(username);
    }
    
    @GetMapping("/findUserByMobile/{mobile}")
    public AuthUser findUserByMobile(@PathVariable("mobile") String mobile){
    	return userService.findUserByMobile(mobile);
    }
    
}

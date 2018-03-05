package com.github.liuweijw.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.domain.User;
import com.github.liuweijw.admin.service.UserService;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
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
     * 通过用户名查询用户及其角色信息
     */
    @GetMapping("/findUserByUsername/{username}")
    public AuthUser findUserByUsername(@PathVariable String username) {
    	return userService.findUserByUsername(username);
    }
    
    /**
     * 通过手机号码查询用户及其角色信息
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public AuthUser findUserByMobile(@PathVariable("mobile") String mobile){
    	return userService.findUserByMobile(mobile);
    }
    
    /**
     * 查询用户列表数据
     */
    @RequestMapping(value="/list",method=RequestMethod.GET)
	public R<PageBean<User>> list(HttpServletRequest request, User user, PageParams pageParams){
    	PageBean<User> pageData = this.userService.findAll(pageParams, user);
    	return new R<PageBean<User>>().data(pageData);
    }
    
    /**
     * 通过userId删除数据
     */
    @RequestMapping(value="/del/{userId}",method=RequestMethod.POST)
	public R<Boolean> list(HttpServletRequest request,@PathVariable("userId") Integer userId){
    	return new R<Boolean>().data(this.userService.delByUserId(userId));
    }
    
    /**
     * 通过userId 查询信息
     */
    @RequestMapping(value="/find/{userId}",method=RequestMethod.GET)
	public R<AuthUser> findByUserId(HttpServletRequest request,@PathVariable("userId") Integer userId){
    	return new R<AuthUser>().data(this.userService.findByUserId(userId));
    }
}

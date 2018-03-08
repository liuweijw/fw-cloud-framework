package com.github.liuweijw.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.beans.UserForm;
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
    	AuthUser authUser = this.userService.findByUserId(String.valueOf(userId));
    	if(null == authUser) return new R<AuthUser>().failure();
    	
    	System.out.println(authUser.toString());
    	return new R<AuthUser>().success().data(authUser);
    }
    
    /**
     * 添加用户
     */
    @RequestMapping(value="/addUser",method=RequestMethod.POST)
	public R<Boolean> addUser(HttpServletRequest request, @RequestBody UserForm userForm){
    	if(null == userForm.getDeptId()) return new R<Boolean>().failure("请选择部门");
    	if(null == userForm.getRoleId()) return new R<Boolean>().failure("请选择角色");
    	User user = new User();
    	user.setCreateTime(new Date());
    	user.setDelFlag(0);
    	user.setDeptId(userForm.getDeptId());
    	user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword().trim()));
    	user.setUpdateTime(new Date());
    	user.setUsername(userForm.getUsername());
    	boolean r = this.userService.addUserAndRole(user,userForm.getRoleId());
    	return new R<Boolean>().data(r);
    }
    
    /**
     * 修改用户
     */
    @RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public R<Boolean> updateUser(HttpServletRequest request, @RequestBody UserForm userForm){
    	if(null == userForm.getUserId()) return new R<Boolean>().failure("用户不存在");
    	if(null == userForm.getDeptId()) return new R<Boolean>().failure("请选择部门");
    	if(null == userForm.getRoleId()) return new R<Boolean>().failure("请选择角色");
    	
    	boolean r = this.userService.updateUserAndRole(userForm);
    	return new R<Boolean>().data(r);
    }
    
    /**
     * 修改用户密码
     */
    @RequestMapping(value="/modifyUser",method=RequestMethod.POST)
	public R<Boolean> modifyUser(HttpServletRequest request, @RequestBody UserForm userForm){
    	if(null == userForm.getUsername()) return new R<Boolean>().failure("用户名不存在");
    	if(null == userForm.getPassword()) return new R<Boolean>().failure("请输入旧密码");
    	if(null == userForm.getNewpassword()) return new R<Boolean>().failure("请输入新密码");
    	
    	User user = this.userService.findUserByUsername(userForm.getUsername().trim(), false);
    	if(null == user) return new R<Boolean>().failure("用户名不存在");
    	
    	if(!new BCryptPasswordEncoder().matches(userForm.getPassword().trim(), user.getPassword()))
    		return new R<Boolean>().failure("旧密码输入错误！");
    	
    	user.setPassword(new BCryptPasswordEncoder().encode(userForm.getNewpassword().trim()));
    	
    	boolean r = this.userService.updateUser(user);
    	
    	return new R<Boolean>().data(r);
    }
    
}

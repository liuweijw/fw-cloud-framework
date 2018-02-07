package com.github.liuweijw.auth.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.core.beans.system.AuthUser;

@FeignClient(name = "business-admin-server", fallback = UserServiceFallback.class)
public interface UserService {

	/**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     */
    @GetMapping("/user/findUserByUsername/{username}")
    AuthUser findUserByUsername(@PathVariable("username") String username);
    
    /**
     * 通过手机号查询用户、角色信息
     */
    @GetMapping("/user/findUserByMobile/{mobile}")
    AuthUser findUserByMobile(@PathVariable("mobile") String mobile);
    
}

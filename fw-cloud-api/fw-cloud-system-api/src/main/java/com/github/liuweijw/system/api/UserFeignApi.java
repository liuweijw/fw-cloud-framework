package com.github.liuweijw.system.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.system.api.hystrix.UserFeignApiHystrix;
import com.github.liuweijw.system.api.model.AuthUser;

@FeignClient(name = "business-admin-server", fallback = UserFeignApiHystrix.class)
public interface UserFeignApi {

	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username
	 *            用户名
	 */
	@GetMapping("/api/findUserByUsername/{username}")
	AuthUser findUserByUsername(@PathVariable("username") String username);

	/**
	 * 通过手机号查询用户、角色信息
	 */
	@GetMapping("/api/findUserByMobile/{mobile}")
	AuthUser findUserByMobile(@PathVariable("mobile") String mobile);

}

package com.github.liuweijw.system.api;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.system.api.hystrix.PermissionFeignApiHystrix;
import com.github.liuweijw.system.api.model.AuthPermission;

/**
 * @author liuweijw
 */
@FeignClient(name = "business-admin-server", fallback = PermissionFeignApiHystrix.class)
public interface PermissionFeignApi {

	/**
	 * 通过角色名查询菜单
	 */
	@GetMapping(value = "/api/findMenuByRole/{roleCode}")
	Set<AuthPermission> findMenuByRole(@PathVariable("roleCode") String roleCode);

}

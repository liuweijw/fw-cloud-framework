package com.github.liuweijw.system.gateway.service;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.core.beans.system.AuthPermission;

/**
 * @author liuweijw
 */
@FeignClient(name = "business-admin-server", fallback = MenuPermissionServiceFallback.class)
public interface MenuPermissionService {
   
	/**
     * 通过角色名查询菜单
     */
    @GetMapping(value = "/api/findMenuByRole/{roleCode}")
    Set<AuthPermission> findMenuByRole(@PathVariable("roleCode") String roleCode);
    
}

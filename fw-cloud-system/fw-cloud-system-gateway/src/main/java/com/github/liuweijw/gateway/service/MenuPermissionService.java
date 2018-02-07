package com.github.liuweijw.gateway.service;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.core.beans.system.AuthMenu;

/**
 * @author liuweijw
 */
@FeignClient(name = "business-admin-server", fallback = MenuPermissionServiceFallback.class)
public interface MenuPermissionService {
   
	/**
     * 通过角色名查询菜单
     */
    @GetMapping(value = "/menu/findMenuByRole/{roleCode}")
    Set<AuthMenu> findMenuByRole(@PathVariable("roleCode") String roleCode);
    
}

package com.github.liuweijw.system.gateway.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface PermissionService {

	/**
     * 判断请求是否有权限
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
    
}

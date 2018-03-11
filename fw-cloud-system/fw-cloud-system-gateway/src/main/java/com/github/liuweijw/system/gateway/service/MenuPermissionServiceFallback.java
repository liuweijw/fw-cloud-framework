package com.github.liuweijw.system.gateway.service;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.github.liuweijw.core.beans.system.AuthPermission;

@Slf4j
@Service
public class MenuPermissionServiceFallback implements MenuPermissionService {

	@Override
	public Set<AuthPermission> findMenuByRole(String roleCode) {
		log.error("调用{}异常:{}", "findMenuByRole", roleCode);
		return null;
	}

}

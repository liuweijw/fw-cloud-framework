package com.github.liuweijw.gateway.service;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.github.liuweijw.core.beans.system.AuthMenu;

@Slf4j
@Service
public class MenuPermissionServiceFallback implements MenuPermissionService {

	@Override
	public Set<AuthMenu> findMenuByRole(String roleCode) {
		log.error("调用{}异常:{}", "findMenuByRole", roleCode);
		return null;
	}

}

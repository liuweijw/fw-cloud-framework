package com.github.liuweijw.system.auth.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.github.liuweijw.core.beans.system.AuthUser;

@Slf4j
@Service
public class UserServiceFallback implements UserService {

	@Override
	public AuthUser findUserByUsername(String username) {
		log.error("调用{}异常:{}", "findUserByUsername", username);
		return null;
	}

	@Override
	public AuthUser findUserByMobile(String mobile) {
		log.error("调用{}异常:{}", "findUserByMobile", mobile);
		return null;
	}

}

package com.github.liuweijw.system.api.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.github.liuweijw.system.api.UserFeignApi;
import com.github.liuweijw.system.api.model.AuthUser;

@Slf4j
@Service
public class UserFeignApiHystrix implements UserFeignApi {

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

package com.github.liuweijw.system.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.core.commons.constants.SecurityConstant;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	@Qualifier("consumerTokenServices")
	private ConsumerTokenServices	consumerTokenServices;

	@PostMapping("/removeToken")
	@CacheEvict(value = SecurityConstant.TOKEN_USER_DETAIL, key = "#accesstoken")
	public R<Boolean> removeToken(String accesstoken) {
		boolean isRemoved = consumerTokenServices.revokeToken(accesstoken);
		return new R<Boolean>().data(isRemoved);
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("ftl/login");
	}
}

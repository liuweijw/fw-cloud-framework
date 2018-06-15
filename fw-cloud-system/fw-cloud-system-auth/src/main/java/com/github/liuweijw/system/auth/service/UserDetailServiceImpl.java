package com.github.liuweijw.system.auth.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.system.api.UserFeignApi;
import com.github.liuweijw.system.api.model.AuthUser;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService, Serializable {

	private static final long	serialVersionUID	= 5181442448895412779L;

	@Autowired
	private UserFeignApi		userFeignApi;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringHelper.isBlank(username))
			throw new UsernameNotFoundException("用户不存在:" + username);

		AuthUser user = userFeignApi.findUserByUsername(username);
		if (null == user)
			throw new UsernameNotFoundException("用户不存在:" + username);

		return new UserDetailsImpl(user);
	}

}

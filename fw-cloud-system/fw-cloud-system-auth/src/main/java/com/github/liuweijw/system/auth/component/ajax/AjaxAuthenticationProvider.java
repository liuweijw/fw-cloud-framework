package com.github.liuweijw.system.auth.component.ajax;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.system.auth.service.UserDetailsImpl;
import com.github.liuweijw.system.auth.service.UserService;

/**
 * @author liuweijw
 */
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	
	private UserService userService;
		
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AjaxAuthenticationToken ajaxAuthenticationToken = (AjaxAuthenticationToken) authentication;
        AuthUser user = userService.findUserByMobile((String) ajaxAuthenticationToken.getPrincipal());

        if (null == user) 
        	throw new UsernameNotFoundException("登录账户[" + ajaxAuthenticationToken.getPrincipal()+"]不存在");
        
        UserDetailsImpl userDetails = buildUserDeatils(user);
        
        if (null == userDetails) 
        	throw new InternalAuthenticationServiceException("登录用户[" + ajaxAuthenticationToken.getPrincipal() + "]不存在！");

        AjaxAuthenticationToken authenticationToken = new AjaxAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(ajaxAuthenticationToken.getDetails());
        return authenticationToken;
    }

    private UserDetailsImpl buildUserDeatils(AuthUser user) {
        return new UserDetailsImpl(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}

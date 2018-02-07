package com.github.liuweijw.gateway.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import com.github.liuweijw.core.beans.system.AuthMenu;
import com.github.liuweijw.core.utils.StringHelper;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    
	@Autowired
    private MenuPermissionService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @SuppressWarnings("unchecked")
	@Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // options 跨域配置，现在处理是通过前端配置代理，不使用这种方式，存在风险
        /*if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            return true;
        }*/
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;
        if (principal != null) {
        	if (CollectionUtils.isEmpty(grantedAuthorityList)) {
                return hasPermission;
            }

            Set<AuthMenu> urls = new HashSet<AuthMenu>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                urls.addAll(menuService.findMenuByRole(authority.getAuthority()));
            }
            
            for (AuthMenu menu : urls) {
            	//System.out.println("=======menu.getUrl()=====" + menu.getUrl());
            	//System.out.println("=======request.getRequestURI()=====" + request.getRequestURI());
                if (StringHelper.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        System.out.println("=======request.hasPermission=====" + hasPermission);
        return hasPermission;
    }
}

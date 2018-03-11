package com.github.liuweijw.system.gateway.service;

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

import com.github.liuweijw.core.beans.system.AuthPermission;
import com.github.liuweijw.core.utils.StringHelper;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
    private MenuPermissionService apiPermissionService;

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

        if (null == principal) return hasPermission;
        if (CollectionUtils.isEmpty(grantedAuthorityList)) return hasPermission;

        // 接口层面做了缓存处理，后续可以继续优化
        Set<AuthPermission> permissions = new HashSet<AuthPermission>();
        for (SimpleGrantedAuthority authority : grantedAuthorityList) {
        	permissions.addAll(apiPermissionService.findMenuByRole(authority.getAuthority()));
        }

        // 网关处理是否拥有菜单权限，菜单下的功能权限校验由调用子模块负责
        String requestURI = request.getRequestURI();
        for (AuthPermission menu : permissions) {
            if (StringHelper.isNotEmpty(menu.getUrl())
            		&& antPathMatcher.match(menu.getUrl(), requestURI)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}

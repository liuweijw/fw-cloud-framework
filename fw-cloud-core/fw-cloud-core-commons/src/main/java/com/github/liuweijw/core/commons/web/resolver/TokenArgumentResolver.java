package com.github.liuweijw.core.commons.web.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.liuweijw.core.beans.system.AuthRole;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.commons.jwt.JwtUtil;
import com.github.liuweijw.core.utils.StringHelper;

/**
 * 将AuthUser参数转换为用户对象
 * 
 * @author liuweijw
 *
 */
@Configuration
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

	private CacheManager cacheManager;

    public TokenArgumentResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(AuthUser.class);
    }

    /**
     * 1. 先读缓存
     * 2. 不存在缓存，解析token 获取用户信息
     * 3. 对API含有 AuthUser入参的接口生效
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
    	
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String token = JwtUtil.getToken(request);
        if (StringHelper.isBlank(token)) {
            return null;
        }
        
        Optional<AuthUser> optional = Optional.ofNullable(cacheManager.getCache(SecurityConstant.TOKEN_USER_DETAIL).get(token, AuthUser.class));
        if (optional.isPresent()) {
            return optional.get();
        }
        
        return optional.orElseGet(() -> generatorByToken(request, token));
    }

    private AuthUser generatorByToken(HttpServletRequest request, String token) {
        String username = JwtUtil.getUserName(request);
        List<String> roles = JwtUtil.getRole(request);
        
        AuthUser authUser = new AuthUser();
        authUser.setUsername(username);
        
        if(null != roles){
        	List<AuthRole> roleList = new ArrayList<AuthRole>();
            roles.stream().forEach(role -> {
            	AuthRole authRole = new AuthRole();
            	authRole.setRoleCode(role);
            	roleList.add(authRole);
            });
            authUser.setRoleList(roleList);
            
            cacheManager.getCache(SecurityConstant.TOKEN_USER_DETAIL).put(token, authUser);
        }
        
        return authUser;
    }

}

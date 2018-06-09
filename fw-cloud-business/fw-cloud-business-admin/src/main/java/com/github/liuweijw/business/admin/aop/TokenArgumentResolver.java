package com.github.liuweijw.business.admin.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.system.api.model.AuthRole;
import com.github.liuweijw.system.api.model.AuthUser;

/**
 * 将AuthUser参数转换为用户对象
 *
 * @author liuweijw
 */
@Slf4j
@Configuration
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(AuthUser.class);
	}

	/**
	 * 1. 先读缓存 2. 不存在缓存，解析token 获取用户信息 3. 对API含有 AuthUser入参的接口生效
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) {

		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

		String username = request.getHeader(SecurityConstant.USER_HEADER);
		String roles = request.getHeader(SecurityConstant.ROLE_HEADER);
		if (StringHelper.isBlank(username) || StringHelper.isBlank(roles)) {
			log.warn("resolveArgument error username or role is empty");
			return null;
		}

		AuthUser authUser = new AuthUser();
		authUser.setUsername(username);
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		Arrays.stream(roles.split(",")).forEach(role -> {
			AuthRole authRole = new AuthRole();
			authRole.setRoleCode(role);
			roleList.add(authRole);
		});
		authUser.setRoleList(roleList);
		return authUser;
	}

}

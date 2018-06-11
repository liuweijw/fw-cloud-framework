package com.github.liuweijw.business.admin.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.commons.utils.RequestUtil;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.business.commons.web.config.PermissionConfiguration;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.core.commons.jwt.JwtUtil;
import com.github.liuweijw.core.configuration.JwtConfiguration;
import com.github.liuweijw.exception.CheckedException;

/**
 * 功能权限切面验证
 * 
 * @author liuweijw
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService		permissionService;

	@Autowired
	private PermissionConfiguration	permissionConfiguration;

	@Autowired
	private ObjectMapper			objectMapper;

	@Autowired
	private JwtConfiguration		jwtConfiguration;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		if (!permissionConfiguration.isEnabled()) return true;
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) return true;

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();

		String requestURI = request.getRequestURI();
		String modulePermission = "";
		// 为了规范，如果class上面没有设置@PrePermissions则不通过
		if (!clazz.isAnnotationPresent(PrePermissions.class)) {
			log.error("请求[" + requestURI + "]模块上未设置权限,请设置注解@PrePermissions权限！");
			R<Boolean> responseWithR = new R<Boolean>().failure(
					"请求[" + requestURI + "]模块上未设置权限,请设置注解@PrePermissions权限！").data(false);
			this.handleWithResponse(response, responseWithR);
			return false;
		}

		PrePermissions clazzPermissions = clazz.getAnnotation(PrePermissions.class);
		if (!clazzPermissions.required()) return true;
		modulePermission = clazzPermissions.value()[0];

		// 为了规范：方法上没设置权限的请求则不通过
		if (!method.isAnnotationPresent(PrePermissions.class)) {
			log.error("请求[" + requestURI + "]方法上未设置权限,请设置注解@PrePermissions权限！");
			R<Boolean> responseWithR = new R<Boolean>().failure(
					"请求[" + requestURI + "]方法上未设置权限,请设置注解@PrePermissions权限！").data(false);
			this.handleWithResponse(response, responseWithR);
			return false;
		}

		PrePermissions prePermissions = method.getAnnotation(PrePermissions.class);
		String[] permissions = prePermissions.value();
		if (null == permissions || permissions.length == 0) {
			log.error("请求[" + requestURI + "]方法上未正确设置权限,请设置注解@PrePermissions权限！");
			R<Boolean> responseWithR = new R<Boolean>().failure(
					"请求[" + requestURI + "]方法上未正确设置权限,请设置注解@PrePermissions权限！").data(false);
			this.handleWithResponse(response, responseWithR);
			return false;
		}

		// 验证是否有功能权限
		List<String> roleList = JwtUtil.getRole(request, jwtConfiguration.getJwtkey());
		if (null == roleList || roleList.size() == 0) {
			R<Boolean> responseWithR = new R<Boolean>().failure("请求[" + requestURI + "]权限验证失败！")
					.data(false);
			this.handleWithResponse(response, responseWithR);
			return false;
		}

		// 所以角色权限集合
		Set<String> menuPermissions = new HashSet<String>();
		for (String roleCode : roleList) {
			menuPermissions.addAll(this.permissionService.findMenuPermissions(roleCode));
		}

		if (null == menuPermissions || menuPermissions.size() == 0) {
			R<Boolean> responseWithR = new R<Boolean>().failure("请求[" + requestURI + "]权限未配置！")
					.data(false);
			this.handleWithResponse(response, responseWithR);
			return false;
		}

		for (String permission : permissions) {
			String valiatePermission = modulePermission + permission;
			log.info("请求[" + requestURI + "],permission:[" + valiatePermission + "]");
			// 验证permission是否有功能权限
			if (!menuPermissions.contains(valiatePermission)) {
				log.info("请求[" + requestURI + "]权限[" + valiatePermission + "]未配置！");
				R<Boolean> responseWithR = new R<Boolean>().failure(
						"请求[" + requestURI + "]权限[" + valiatePermission + "]未配置！").data(false);
				this.handleWithResponse(response, responseWithR);
				return false;
			}
		}

		return true;
	}

	public void handleWithResponse(HttpServletResponse response, R<Boolean> responseWithR) {
		try {
			RequestUtil.handleWithResponse(response, this.objectMapper
					.writeValueAsString(responseWithR));
		} catch (IOException e) {
			e.printStackTrace();
			throw new CheckedException("Failed to response");
		}
	}

}

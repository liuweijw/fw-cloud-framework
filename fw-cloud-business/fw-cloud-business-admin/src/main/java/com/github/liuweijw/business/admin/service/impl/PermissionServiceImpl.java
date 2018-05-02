package com.github.liuweijw.business.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.QRoleMenu;
import com.github.liuweijw.business.admin.domain.QRoleMenuPermission;
import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.domain.RoleMenuPermission;
import com.github.liuweijw.business.admin.repository.RoleRepository;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;

@Component
public class PermissionServiceImpl extends JPAFactoryImpl implements PermissionService {

	@Autowired
	private RoleRepository	roleRepository;

	@Override
	@Cacheable(value = AdminCacheKey.PERMISSION_INFO, key = AdminCacheKey.PERMISSION_INFO_KEY_ROLECODE)
	public Set<String> findMenuPermissions(String roleCode) {
		Set<String> permissions = new HashSet<>();
		// 查询Role
		Role role = roleRepository.findRoleByRoleCode(roleCode.trim());
		if (null == role) return permissions;
		// 查询菜单
		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		QRoleMenuPermission qRoleMenuPermission = QRoleMenuPermission.roleMenuPermission;
		List<RoleMenuPermission> rList = this.queryFactory.select(qRoleMenuPermission).from(
				qRoleMenuPermission, qRoleMenu).where(qRoleMenu.roleId.eq(role.getRoleId())).where(
				qRoleMenuPermission.roleMenuId.eq(qRoleMenu.id)).fetch();

		if (null == rList || rList.size() == 0) return permissions;

		rList.stream().forEach(r -> {
			permissions.add(r.getPermission());
		});

		return permissions;
	}

}

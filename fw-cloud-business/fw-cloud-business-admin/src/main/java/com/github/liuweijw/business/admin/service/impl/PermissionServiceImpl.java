package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.QRoleMenu;
import com.github.liuweijw.business.admin.domain.QRoleMenuPermission;
import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.domain.RoleMenu;
import com.github.liuweijw.business.admin.domain.RoleMenuPermission;
import com.github.liuweijw.business.admin.repository.RoleMenuPermissionRepository;
import com.github.liuweijw.business.admin.repository.RoleMenuRepository;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.admin.service.RoleService;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;

@Component
public class PermissionServiceImpl extends JPAFactoryImpl implements PermissionService {

	@Autowired
	private RoleService						roleService;

	@Autowired
	private RoleMenuRepository				roleMenuRepository;

	@Autowired
	private RoleMenuPermissionRepository	roleMenuPermissionRepository;

	@Override
	@Cacheable(cacheNames = AdminCacheKey.PERMISSION_INFO, key = "'permission_' + #roleId")
	public List<RoleMenuPermission> findMenuPermissionByRoleId(Integer roleId) {
		if (null == roleId) return null;
		// 查询菜单
		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		QRoleMenuPermission qRoleMenuPermission = QRoleMenuPermission.roleMenuPermission;
		List<RoleMenuPermission> rList = this.queryFactory.select(qRoleMenuPermission).from(
				qRoleMenuPermission, qRoleMenu).where(qRoleMenu.roleId.eq(roleId)).where(
				qRoleMenuPermission.roleMenuId.eq(qRoleMenu.id)).fetch();
		return rList;
	}

	@Override
	public Set<String> findMenuPermissions(String roleCode) {
		Set<String> permissions = new HashSet<>();
		// 查询Role
		Role role = roleService.findRoleByCode(roleCode.trim());
		if (null == role) return permissions;
		// 查询菜单
		List<RoleMenuPermission> rList = this.findMenuPermissionByRoleId(role.getRoleId());
		if (null == rList || rList.size() == 0) return permissions;

		rList.stream().forEach(r -> {
			permissions.add(r.getPermission());
		});

		return permissions;
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.PERMISSION_INFO, AdminCacheKey.MENU_INFO,
			AdminCacheKey.MODULE_INFO, AdminCacheKey.ROLE_INFO }, allEntries = true)
	@Transactional
	public boolean updateRoleMenuPermissions(String roleCode, String... permissions) {
		Role role = roleService.findRoleByCode(roleCode.trim());
		if (null == role) return false;

		// 菜单集合
		Map<Integer, List<String>> roleMenuIdList = new HashMap<Integer, List<String>>();
		for (String permission : permissions) {
			if (!permission.contains("|")) continue;
			String[] menuPermissions = permission.split("\\|");
			Integer menuId = WebUtils.parseStrToInteger(menuPermissions[0], null);
			if (null == menuId || StringHelper.isBlank(menuPermissions[1])) continue;
			if (!roleMenuIdList.containsKey(menuId)) {
				roleMenuIdList.put(menuId, new ArrayList<String>());
			}
			roleMenuIdList.get(menuId).add(menuPermissions[1].trim());
		}

		// 删除RoleMenu和RoleMenuPermission
		this.delRoleMenuPermission(role.getRoleId());

		if (roleMenuIdList.size() > 0) {
			roleMenuIdList.forEach((menuId, menuPermissions) -> {
				RoleMenu menuRole = new RoleMenu();
				menuRole.setMenuId(menuId);
				menuRole.setRoleId(role.getRoleId());
				menuRole = roleMenuRepository.saveAndFlush(menuRole);
				Integer menuRoleId = menuRole.getId();

				menuPermissions.forEach(p -> {
					String permission = p.trim();
					if (permission.contains("_")) {
						RoleMenuPermission roleMenuPermission = new RoleMenuPermission();
						roleMenuPermission.setRoleMenuId(menuRoleId);
						roleMenuPermission.setPermission(permission);
						roleMenuPermissionRepository.saveAndFlush(roleMenuPermission);
					}
				});
			});
		}

		return true;
	}

	private boolean delRoleMenuPermission(Integer roleId) {
		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		List<RoleMenu> roleMeunList = this.queryFactory.selectFrom(qRoleMenu).where(
				qRoleMenu.roleId.eq(roleId.intValue())).fetch();

		Set<Integer> roleMenuIdList = new HashSet<Integer>();
		roleMeunList.forEach(r -> {
			roleMenuIdList.add(r.getId());
			roleMenuRepository.delete(r.getId());
		});

		Integer[] roleMenuIdArray = new Integer[roleMenuIdList.size()];
		return this.delRoleMenuPermissionByRoleMenuId(roleMenuIdList.toArray(roleMenuIdArray));
	}

	private boolean delRoleMenuPermissionByRoleMenuId(Integer... roleMenuArray) {
		QRoleMenuPermission qRoleMenuPermission = QRoleMenuPermission.roleMenuPermission;
		long num = this.queryFactory.delete(qRoleMenuPermission).where(
				qRoleMenuPermission.roleMenuId.in(roleMenuArray)).execute();
		return num > 0;
	}

}

package com.github.liuweijw.business.admin.service;

import java.util.Set;

/**
 * 权限服务
 * 
 * @author liuweijw
 */
public interface PermissionService {

	/**
	 * 通过角色获取菜单权限列表
	 *
	 * @param roleCode
	 *            角色
	 * @return 权限列表
	 */
	Set<String> findMenuPermissions(String roleCode);

	/**
	 * 更新角色-菜单-权限关联关系
	 * 
	 * @param roleCode
	 *            角色编码
	 * @param permissions
	 *            权限列表 菜单Id|菜单path_module名称 ex: 2|user_add
	 * @return
	 */
	boolean updateRoleMenuPermissions(String roleCode, String... permissions);

}

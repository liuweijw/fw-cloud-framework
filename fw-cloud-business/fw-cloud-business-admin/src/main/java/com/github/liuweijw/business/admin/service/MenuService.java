package com.github.liuweijw.business.admin.service;

import java.util.List;
import java.util.Set;

import com.github.liuweijw.business.admin.domain.Menu;
import com.github.liuweijw.business.commons.tree.MenuTree;
import com.github.liuweijw.core.beans.system.AuthMenu;

public interface MenuService {

	/**
	 * 通过角色名称查询URL 权限
	 *
	 * @param roleCode
	 *            角色名称
	 * @return 菜单列表
	 */
	Set<AuthMenu> findMenuByRole(String roleCode);

	/**
	 * 级联删除菜单
	 *
	 * @param id
	 *            菜单ID
	 * @param roleCode
	 *            角色
	 * @return 成功、失败
	 */
	Boolean deleteMenu(Integer id, String roleCode);

	/**
	 * 更新菜单信息
	 *
	 * @param sysMenu
	 *            菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(Menu sysMenu, String roleCode);

	/**
	 * 返回角色的菜单
	 *
	 * @param roleCode
	 *            角色
	 * @return 菜单列表
	 */
	List<MenuTree> findRoleMenuTree(String roleCode);

	/**
	 * 返回所有菜单树形数据 带用户功能权限
	 */
	List<MenuTree> findAllMenuTree();

	/**
	 * 仅返回所有菜单树形数据
	 */
	List<MenuTree> findAllMenuTreeList();

	/**
	 * 通过id删除
	 */
	boolean delById(Integer menuId);

	/**
	 * 菜单新增修改
	 */
	Menu saveOrUpdate(Menu menu);

	/**
	 * 仅返回所有菜单数据
	 */
	List<Menu> findMenuList();

	/**
	 * 返回roleId关联所有菜单数据
	 */
	List<Menu> findMenuByRoleId(Integer roleId);
}

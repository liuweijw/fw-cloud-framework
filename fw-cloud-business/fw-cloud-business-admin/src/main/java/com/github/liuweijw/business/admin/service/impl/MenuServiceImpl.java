package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.Menu;
import com.github.liuweijw.business.admin.domain.Module;
import com.github.liuweijw.business.admin.domain.QMenu;
import com.github.liuweijw.business.admin.domain.QRoleMenu;
import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.repository.MenuRepository;
import com.github.liuweijw.business.admin.repository.RoleRepository;
import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.admin.service.ModuleService;
import com.github.liuweijw.business.commons.tree.MenuTree;
import com.github.liuweijw.business.commons.tree.TreeUtil;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.core.beans.system.AuthMenu;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.utils.Assert;
import com.github.liuweijw.core.utils.StringHelper;

@Component
public class MenuServiceImpl extends JPAFactoryImpl implements MenuService {

	@Autowired
	private RoleRepository	roleRepository;

	@Autowired
	private MenuRepository	menuRepository;

	@Autowired
	private ModuleService	moduleService;

	@Override
	@Cacheable(value = AdminCacheKey.MENU_INFO, key = AdminCacheKey.MENU_INFO_KEY_ROLECODE)
	public Set<AuthMenu> findMenuByRole(String roleCode) {
		if (StringHelper.isBlank(roleCode)) return null;

		Role role = roleRepository.findRoleByRoleCode(roleCode.trim());
		if (null == role) return null;

		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		QMenu qMenu = QMenu.menu;
		List<Menu> rList = this.queryFactory.select(qMenu).from(qRoleMenu, qMenu).where(
				qRoleMenu.roleId.eq(role.getRoleId())).where(qRoleMenu.menuId.eq(qMenu.menuId))
				.fetch();

		if (null == rList || rList.size() == 0) return null;

		Set<AuthMenu> mList = new HashSet<AuthMenu>();
		for (Menu m : rList) {
			AuthMenu authMenu = new AuthMenu();
			BeanUtils.copyProperties(m, authMenu);
			mList.add(authMenu);
		}
		return mList;
	}

	@Override
	@CacheEvict(value = AdminCacheKey.MENU_INFO, allEntries = true)
	@Transactional
	public Boolean deleteMenu(Integer menuId, String roleCode) {
		Assert.isNull(menuId, "菜单ID不能为空");

		// 删除当前节点 -- 假删除
		QMenu qMenu = QMenu.menu;
		this.queryFactory.update(qMenu).set(qMenu.statu, CommonConstant.STATUS_DEL).where(
				qMenu.menuId.eq(menuId)).execute();

		// 删除父节点为当前节点的节点 -- 假删除
		this.queryFactory.update(qMenu).set(qMenu.statu, CommonConstant.STATUS_DEL).where(
				qMenu.pid.eq(menuId)).execute();

		return true;
	}

	@Override
	@CacheEvict(value = AdminCacheKey.MENU_INFO, allEntries = true)
	public Boolean updateMenuById(Menu menu, String roleCode) {

		if (null == menu || null == menu.getMenuId()) return null;

		menuRepository.saveAndFlush(menu);

		return true;
	}

	@Override
	public List<MenuTree> findUserMenuTree(String roleName) {
		Set<AuthMenu> menuList = findMenuByRole(roleName);
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		menuList.forEach(menu -> {
			menuTreeList.add(new MenuTree(menu));
		});
		return TreeUtil.build(menuTreeList, 0);
	}

	// 目前只支持二级菜单，若有三级，则改递归即可
	@Override
	@Cacheable(value = AdminCacheKey.MENU_INFO)
	public List<MenuTree> findUserMenuTree() {
		QMenu qMenu = QMenu.menu;
		List<Menu> rList = this.queryFactory.selectFrom(qMenu).fetch();
		if (null == rList || rList.size() == 0) return null;

		List<Module> moduleList = moduleService.getAllList();
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		rList.forEach(menu -> {
			if (menu.getPid() != 0 && null != moduleList && moduleList.size() > 0) {
				moduleList.forEach(m -> {
					String id = menu.getPath() + "_" + m.getCode();
					menuTreeList.add(new MenuTree(id, menu.getMenuId() + "", m.getName()));
				});
			}
			menuTreeList.add(new MenuTree(menu.getMenuId() + "", menu.getPid() + "", menu
					.getMenuName()));
		});

		return TreeUtil.build(menuTreeList, "0");
	}
}

package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.admin.service.ModuleService;
import com.github.liuweijw.business.admin.service.RoleService;
import com.github.liuweijw.business.commons.tree.MenuTree;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.base.tree.TreeUtil;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.beans.system.AuthMenu;
import com.github.liuweijw.core.commons.constants.CommonConstant;

@Component
@CacheConfig(cacheNames = AdminCacheKey.MENU_INFO)
public class MenuServiceImpl extends JPAFactoryImpl implements MenuService {

	@Autowired
	private RoleService		roleService;

	@Autowired
	private MenuRepository	menuRepository;

	@Autowired
	private ModuleService	moduleService;

	@Override
	public Set<AuthMenu> findMenuByRole(String roleCode) {
		if (StringHelper.isBlank(roleCode)) return null;

		Role role = roleService.findRoleByCode(roleCode.trim());
		if (null == role) return null;

		List<Menu> rList = this.findMenuByRoleId(role.getRoleId());
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
	public List<MenuTree> findRoleMenuTree(String roleCode) {
		Set<AuthMenu> menuList = findMenuByRole(roleCode);
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		menuList.forEach(menu -> {
			menuTreeList.add(new MenuTree(menu));
		});
		return TreeUtil.build(menuTreeList, 0);
	}

	// 目前只支持二级菜单，若有三级，则改递归即可
	@Override
	public List<MenuTree> findAllMenuTree() {
		List<Menu> rList = this.findMenuList();

		List<Module> moduleList = this.moduleService.getAllList();
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

	@Override
	public List<MenuTree> findAllMenuTreeList() {
		List<Menu> rList = this.findMenuList();

		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		rList.forEach(menu -> {
			AuthMenu authMenu = new AuthMenu();
			BeanUtils.copyProperties(menu, authMenu);
			menuTreeList.add(new MenuTree(authMenu));
		});

		return TreeUtil.build(menuTreeList, "0");
	}

	@Override
	@Cacheable(key = "'menu_list'")
	public List<Menu> findMenuList() {
		QMenu qMenu = QMenu.menu;
		List<Menu> rList = this.queryFactory.selectFrom(qMenu).fetch();

		return rList;
	}

	@Override
	@Cacheable(key = "'menu_' + #roleId")
	public List<Menu> findMenuByRoleId(Integer roleId) {
		if (null == roleId) return null;

		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		QMenu qMenu = QMenu.menu;
		List<Menu> rList = this.queryFactory.select(qMenu).from(qRoleMenu, qMenu).where(
				qRoleMenu.roleId.eq(roleId)).where(qRoleMenu.menuId.eq(qMenu.menuId)).fetch();
		return rList;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean delById(Integer menuId) {
		QMenu qMenu = QMenu.menu;
		long num = this.queryFactory.delete(qMenu).where(qMenu.menuId.eq(menuId.intValue()))
				.execute();
		return num > 0;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Menu saveOrUpdate(Menu menu) {
		if (null == menu) return null;

		return menuRepository.saveAndFlush(menu);
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Boolean deleteMenu(Integer menuId, String roleCode) {
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
	@Transactional
	@CacheEvict(allEntries = true)
	public Boolean updateMenuById(Menu menu, String roleCode) {
		if (null == menu || null == menu.getMenuId()) return null;
		menuRepository.saveAndFlush(menu);
		return true;
	}

}

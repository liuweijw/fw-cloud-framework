package com.github.liuweijw.business.admin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.beans.MenuTreeBean;
import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.tree.MenuTree;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.utils.R;

/**
 * 菜单服务
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping("/menu")
@PrePermissions(value = Module.MENU)
public class MenuController extends BaseController {

	@Autowired
	private MenuService			menuService;

	@Autowired
	private PermissionService	permissionService;

	@GetMapping(value = "/menuTreeList/{roleCode}")
	@PrePermissions(value = Functional.VIEW)
	public R<MenuTreeBean> menuTreeList(@PathVariable("roleCode") String roleCode) {
		MenuTreeBean menuTreeBean = new MenuTreeBean();

		List<MenuTree> menuTree = menuService.findUserMenuTree();
		menuTreeBean.setMenuList(menuTree);

		Set<String> permissions = new HashSet<String>();
		permissions.addAll(permissionService.findMenuPermissions(roleCode));

		menuTreeBean.setPermissions(permissions.toArray(new String[permissions.size()]));

		return new R<MenuTreeBean>().data(menuTreeBean);
	}

}

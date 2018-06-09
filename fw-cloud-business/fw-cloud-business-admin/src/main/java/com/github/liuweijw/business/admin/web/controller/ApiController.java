package com.github.liuweijw.business.admin.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.beans.UserBean;
import com.github.liuweijw.business.admin.domain.Dict;
import com.github.liuweijw.business.admin.service.DictService;
import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.admin.service.UserService;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.core.beans.system.AuthMenu;
import com.github.liuweijw.core.commons.jwt.JwtUtil;
import com.github.liuweijw.core.configuration.JwtConfiguration;
import com.github.liuweijw.system.api.model.AuthUser;

/**
 * 无须经过网关权限的接口
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping("/api")
@PrePermissions(value = Module.API, required = false)
public class ApiController extends BaseController {

	@Autowired
	private UserService			userService;

	@Autowired
	private MenuService			menuService;

	@Autowired
	private PermissionService	permissionService;

	@Autowired
	private DictService			dictService;

	@Autowired
	private JwtConfiguration	jwtConfiguration;

	/**
	 * 获取当前用户信息（角色、权限）
	 */
	@GetMapping("/info")
	public R<UserBean> user(AuthUser user) {
		return new R<UserBean>().data(userService.findUserInfo(user));
	}

	/**
	 * 返回当前用户树形菜单集合
	 * 
	 * @return 树形菜单
	 */
	@GetMapping("/userTree")
	public R<List<Integer>> userTree(HttpServletRequest request) {
		String roleCode = JwtUtil.getRole(request, jwtConfiguration.getJwtkey()).get(0);
		Set<AuthMenu> menus = menuService.findMenuByRole(roleCode);

		List<Integer> menuList = new ArrayList<>();
		if (null == menus || menus.size() == 0) return new R<List<Integer>>().data(menuList);

		menus.stream().forEach(menu -> {
			menuList.add(menu.getMenuId());
		});

		return new R<List<Integer>>().data(menuList);
	}

	/**
	 * 通过角色获取菜单相关权限列表
	 */
	@GetMapping(value = "/findMenuPermissions/{roleCode}")
	public Set<String> findMenuPermissions(@PathVariable("roleCode") String roleCode) {

		return permissionService.findMenuPermissions(roleCode);
	}

	/**
	 * 通过type获取字典数据
	 */
	@GetMapping("/dictType/{type}")
	public R<List<Dict>> findDictByType(@PathVariable String type) {
		return new R<List<Dict>>().data(dictService.getDictList(type));
	}
}

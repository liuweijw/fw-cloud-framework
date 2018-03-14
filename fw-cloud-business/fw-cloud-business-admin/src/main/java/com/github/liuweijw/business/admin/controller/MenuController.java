package com.github.liuweijw.business.admin.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.utils.R;

/**
 * 菜单服务
 * 
 * @author liuweijw
 *
 */
@RestController
@RequestMapping("/menu")
@PrePermissions(value = Module.MENU)
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/permissions/{roleCode}",method=RequestMethod.GET)
    @PrePermissions(value = Functional.VIEW)
	public R<Set<String>> getPermissionsList(@PathVariable("roleCode") String roleCode){

		return new R<Set<String>>().data(menuService.findMenuPermissions(roleCode));
    }
	
}

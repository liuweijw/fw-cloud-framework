package com.github.liuweijw.business.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.service.MenuService;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;

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
	
}

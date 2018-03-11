package com.github.liuweijw.business.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.service.RoleService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.utils.R;

/**
 * 角色服务
 * 
 * @author liuweijw
 *
 */
@RestController
@RequestMapping("/role")
@PrePermissions(value = Module.ROLE)
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/listByDeptId/{deptId}")
	@PrePermissions(value = Functional.VIEW)
    public R<List<Role>> getRoleListByDeptId(@PathVariable("deptId") Integer deptId) {
        return new R<List<Role>>().data(roleService.getRoleListByDeptId(deptId));
    }
	
	@GetMapping(value = "/list")
	@PrePermissions(value = Functional.VIEW)
    public R<List<Role>> getRoleList() {
        return new R<List<Role>>().data(roleService.getRoleList());
    }
	
}

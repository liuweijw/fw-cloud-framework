package com.github.liuweijw.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.service.RoleService;
import com.github.liuweijw.core.commons.web.BaseController;
import com.github.liuweijw.core.utils.R;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/listByDeptId/{deptId}")
    public R<List<Role>> getRoleListByDeptId(@PathVariable("deptId") Integer deptId) {
        return new R<List<Role>>().data(roleService.getRoleListByDeptId(deptId));
    }
	
	@GetMapping(value = "/list")
    public R<List<Role>> getRoleList() {
        return new R<List<Role>>().data(roleService.getRoleList());
    }
	
}

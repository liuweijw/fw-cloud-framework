package com.github.liuweijw.business.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.service.DeptService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.tree.DeptTree;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.utils.R;

/**
 * 部门管理服务
 * 
 * @author liuweijw
 *
 */
@RestController
@RequestMapping("/dept")
@PrePermissions(value = Module.DEPT)
public class DeptController extends BaseController {

	@Autowired
	private DeptService deptService;
	
    @GetMapping(value = "/tree")
    @PrePermissions(value = Functional.VIEW)
    public R<List<DeptTree>> getDeptTreeList() {
        return new R<List<DeptTree>>().data(deptService.getDeptTreeList());
    }

}

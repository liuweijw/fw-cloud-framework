package com.github.liuweijw.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.admin.service.DeptService;
import com.github.liuweijw.business.commons.tree.DeptTree;
import com.github.liuweijw.core.commons.web.BaseController;
import com.github.liuweijw.core.utils.R;

@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

	@Autowired
	private DeptService deptService;
	
    @GetMapping(value = "/tree")
    public R<List<DeptTree>> getDeptTreeList() {
        return new R<List<DeptTree>>().data(deptService.getDeptTreeList());
    }

}

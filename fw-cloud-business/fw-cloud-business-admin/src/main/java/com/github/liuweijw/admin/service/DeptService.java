package com.github.liuweijw.admin.service;

import java.util.List;

import com.github.liuweijw.business.commons.tree.DeptTree;

public interface DeptService {

	/**
	 * 得到部门树形列表数据
	 */
	List<DeptTree> getDeptTreeList();

}

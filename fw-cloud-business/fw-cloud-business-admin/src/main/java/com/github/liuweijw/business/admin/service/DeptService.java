package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.domain.Dept;
import com.github.liuweijw.business.commons.tree.DeptTree;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;

public interface DeptService {

	/**
	 * 得到部门树形列表数据
	 */
	List<DeptTree> getDeptTreeList();

	/**
	 * 通过Id查询部门信息
	 * 
	 * @param id
	 *            部门id
	 */
	Dept findById(Integer id);

	/**
	 * 新增部门信息
	 * 
	 * @param dept
	 * @return
	 *         部门
	 */
	Dept saveOrUpdate(Dept dept);

	/**
	 * 删除部门信息
	 * 
	 * @param id
	 */
	boolean delById(Integer id);

	/**
	 * 查询部分分页信息
	 * 
	 * @param pageParams
	 *            分页参数
	 * @param dept
	 *            部门bean
	 * @return
	 *         分页数据
	 */
	PageBean<Dept> findAll(PageParams pageParams, Dept dept);

}

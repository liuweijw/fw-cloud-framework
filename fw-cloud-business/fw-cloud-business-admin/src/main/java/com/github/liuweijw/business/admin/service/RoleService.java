package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;

public interface RoleService {

	List<Role> getRoleListByDeptId(Integer deptId);

	List<Role> getRoleList();

	PageBean<Role> findAll(PageParams pageParams, Role role);

	Role saveOrUpdate(Role role);

	boolean delById(Integer roleId);

	Role findRoleByCode(String roleCode);

}

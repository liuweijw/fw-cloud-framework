package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.domain.Role;

public interface RoleService {

	List<Role> getRoleListByDeptId(Integer deptId);

	List<Role> getRoleList();

}

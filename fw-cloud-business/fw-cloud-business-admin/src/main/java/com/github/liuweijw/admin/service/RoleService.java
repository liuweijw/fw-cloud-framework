package com.github.liuweijw.admin.service;

import java.util.List;

import com.github.liuweijw.admin.domain.Role;

public interface RoleService {

	List<Role> getRoleListByDeptId(Integer deptId);

	List<Role> getRoleList();

}

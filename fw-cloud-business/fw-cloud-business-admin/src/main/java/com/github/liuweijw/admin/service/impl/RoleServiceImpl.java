package com.github.liuweijw.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.admin.domain.QRole;
import com.github.liuweijw.admin.domain.QRoleDept;
import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.repository.RoleRepository;
import com.github.liuweijw.admin.service.RoleService;

@Component
public class RoleServiceImpl extends JPAFactoryImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getRoleListByDeptId(Integer deptId) {
		if(null == deptId || deptId <= 0) return null;
		
		// load role
		QRoleDept qRoleDept = QRoleDept.roleDept;
		QRole qRole = QRole.role;
		List<Role> rList = this.queryFactory.select(qRole)
											.from(qRoleDept,qRole)
											.where(qRoleDept.deptId.eq(deptId))
											.where(qRoleDept.roleId.eq(qRole.roleId))
											.fetch();
		
		return rList;
	}
	
}

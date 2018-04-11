package com.github.liuweijw.business.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.business.admin.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>,
		QueryDslPredicateExecutor<Role> {

	Role findRoleByRoleCode(String roleCode);

}

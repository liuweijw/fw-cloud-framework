package com.github.liuweijw.business.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.business.admin.domain.RoleMenuPermission;

public interface RoleMenuPermissionRepository extends JpaRepository<RoleMenuPermission, Integer>, QueryDslPredicateExecutor<RoleMenuPermission> {

}

package com.github.liuweijw.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.admin.domain.RoleMenuPermission;

public interface RoleMenuPermissionRepository extends JpaRepository<RoleMenuPermission, Integer>, QueryDslPredicateExecutor<RoleMenuPermission> {

}

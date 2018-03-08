package com.github.liuweijw.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.admin.domain.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, QueryDslPredicateExecutor<UserRole> {

}

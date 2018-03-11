package com.github.liuweijw.business.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.business.admin.domain.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer>, QueryDslPredicateExecutor<Dept> {

}

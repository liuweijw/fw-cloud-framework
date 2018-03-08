package com.github.liuweijw.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.admin.domain.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer>, QueryDslPredicateExecutor<Dept> {

}

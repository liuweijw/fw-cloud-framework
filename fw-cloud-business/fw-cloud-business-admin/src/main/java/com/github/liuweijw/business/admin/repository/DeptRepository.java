package com.github.liuweijw.business.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.business.admin.domain.Dept;
import com.github.liuweijw.business.admin.domain.RoleDept;

public interface DeptRepository extends JpaRepository<Dept, Integer>,
		QueryDslPredicateExecutor<Dept> {

	@Query(value = "select * from " + Dept.TABLE_NAME + " where dept_id in( select dept_id from " + RoleDept.TABLE_NAME + ")", nativeQuery = true)
	List<Dept> findRoleAndDeptList();
}

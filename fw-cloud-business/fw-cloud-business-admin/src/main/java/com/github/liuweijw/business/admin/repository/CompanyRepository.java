package com.github.liuweijw.business.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.business.admin.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>, QueryDslPredicateExecutor<Company> {

}

package com.github.liuweijw.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.github.liuweijw.admin.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>, QueryDslPredicateExecutor<Menu> {

}

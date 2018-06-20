package com.github.liuweijw.business.commons.web.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class JPAFactoryImpl {

	// JPA查询工厂
	protected JPAQueryFactory	queryFactory;

	@Autowired
	protected EntityManager		em;

	@PostConstruct
	public void initFactory() {
		queryFactory = new JPAQueryFactory(em);
	}

}

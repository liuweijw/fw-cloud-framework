package com.github.liuweijw.business.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.domain.DeptRelation;
import com.github.liuweijw.business.admin.domain.QDeptRelation;
import com.github.liuweijw.business.admin.repository.DeptRelationRepository;
import com.github.liuweijw.business.admin.service.DeptRelationService;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;

@Component
public class DeptRelationServiceImpl extends JPAFactoryImpl implements DeptRelationService {

	@Autowired
	private DeptRelationRepository	deptRelationRepository;

	@Override
	public List<DeptRelation> findListByPreId(Integer preId) {
		if (null == preId || preId < 0) return null;

		QDeptRelation qDeptRelation = QDeptRelation.deptRelation;
		return this.queryFactory
				.selectFrom(qDeptRelation)
				.where(qDeptRelation.preId.eq(preId))
				.fetch();
	}

	@Override
	@Transactional
	public void saveOrUpdate(DeptRelation deptRelation) {

		deptRelationRepository.save(deptRelation);
	}

}

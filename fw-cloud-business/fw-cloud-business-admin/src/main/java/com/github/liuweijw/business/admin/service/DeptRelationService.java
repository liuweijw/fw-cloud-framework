package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.domain.DeptRelation;

public interface DeptRelationService {

	List<DeptRelation> findListByPreId(Integer preId);

	void saveOrUpdate(DeptRelation deptRelation);

}

package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.admin.domain.Dict;
import com.github.liuweijw.business.admin.domain.QDict;
import com.github.liuweijw.business.admin.repository.DictRepository;
import com.github.liuweijw.business.admin.service.DictService;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.core.utils.StringHelper;

@Component
public class DictServiceImpl extends JPAFactoryImpl implements DictService {

	@Autowired
	private DictRepository	dictRepository;

	@Override
	public List<Dict> getAllList() {
		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).where(qDict.statu.eq(0)).fetch();
	}

	@Override
	public Dict findById(Integer id) {
		if (null == id || id < 0) return null;

		return dictRepository.findOne(id);
	}

	@Override
	@Cacheable(value = "dict_list_", key = "#type")
	public List<Dict> getDictList(String type) {
		List<Dict> dictList = new ArrayList<Dict>();
		if (StringHelper.isBlank(type)) return dictList;

		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).where(qDict.type.eq(type.trim())).where(
				qDict.statu.eq(0)).fetch();
	}

}

package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.Dict;
import com.github.liuweijw.business.admin.domain.QDict;
import com.github.liuweijw.business.admin.repository.DictRepository;
import com.github.liuweijw.business.admin.service.DictService;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.core.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
public class DictServiceImpl extends JPAFactoryImpl implements DictService {

	@Autowired
	private DictRepository	dictRepository;

	// @Cacheable(value=AdminCacheKey.DICT_INFO_LIST, key="#pageParams.pageNo_#pageParams.pageNum", condition="#user.id%2==0")
	@Override
	public PageBean<Dict> findAll(PageParams pageParams, Dict dict) {
		QDict qDict = QDict.dict;
		// 用户名查询条件
		Predicate qLabelPredicate = null;
		Predicate qTypePredicate = null;
		if (null != dict) {
			if (StringHelper.isNotBlank(dict.getLabel())) {
				qLabelPredicate = qDict.label.like("%" + dict.getLabel().trim() + "%");
			}
			if (StringHelper.isNotBlank(dict.getType())) {
				qTypePredicate = qDict.type.like("%" + dict.getType().trim() + "%");
			}
		}

		Predicate predicate = qDict.id.goe(0).and(qTypePredicate).and(qLabelPredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		PageRequest pageRequest = new PageRequest(pageParams.getCurrentPage(), pageParams
				.getPageSize(), sort);
		Page<Dict> pageList = dictRepository.findAll(predicate, pageRequest);

		PageBean<Dict> pageData = new PageBean<Dict>();
		pageData.setCurrentPage(pageParams.getCurrentPage());
		pageData.setPageSize(pageParams.getPageSize());
		pageData.setTotal(pageList.getTotalElements());
		pageData.setList(pageList.getContent());

		return pageData;
	}

	@Override
	@Cacheable(value = AdminCacheKey.DICT_INFO, key = "'dict_list'")
	public List<Dict> getAllList() {
		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).fetch();
	}

	@Override
	@Cacheable(value = AdminCacheKey.DICT_INFO, key = "'dict_' + #id")
	public Dict findById(Integer id) {
		if (null == id || id < 0) return null;

		return dictRepository.findOne(id);
	}

	@Override
	@Cacheable(value = AdminCacheKey.DICT_INFO, key = "'dict_' + #type")
	public List<Dict> getDictList(String type) {
		List<Dict> dictList = new ArrayList<Dict>();
		if (StringHelper.isBlank(type)) return dictList;

		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).where(qDict.type.eq(type.trim())).fetch();
	}

	@Override
	@Transactional
	public Dict saveOrUpdate(Dict dict) {
		if (null == dict) return null;

		Dict dbDict = this.dictRepository.saveAndFlush(dict);

		this.redisCacheClear();

		return dbDict;
	}

	@Override
	@Transactional
	public boolean delById(Integer id) {
		if (null == id || id <= 0) return Boolean.FALSE;

		QDict qDict = QDict.dict;
		long num = this.queryFactory.update(qDict).set(qDict.statu, 1) // 0 正常 1删除
				.where(qDict.id.eq(id.intValue())).execute();

		this.redisCacheClear();
		return num > 0;
	}

	@CacheEvict(value = { AdminCacheKey.DICT_INFO }, allEntries = true)
	public void redisCacheClear() {
	}
}

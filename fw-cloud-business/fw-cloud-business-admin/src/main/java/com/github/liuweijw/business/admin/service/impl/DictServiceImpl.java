package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.commons.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
@CacheConfig(cacheNames = AdminCacheKey.DICT_INFO)
public class DictServiceImpl extends JPAFactoryImpl implements DictService {

	@Autowired
	private DictRepository	dictRepository;

	@Override
	@Cacheable(key = "'page_dict_' + #p0.currentPage + '_' + #p0.pageSize + '_' + #p1.type + '_' + #p1.label")
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
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<Dict> pageList = dictRepository.findAll(predicate, pageRequest);
		return PageUtils.of(pageList);
	}

	@Override
	@Cacheable(key = "'dict_list'", unless = "#result eq null")
	public List<Dict> getAllList() {
		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).fetch();
	}

	@Override
	@Cacheable(key = "'dict_' + #id", unless = "#result eq null")
	public Dict findById(Integer id) {
		if (null == id || id < 0) return null;

		return dictRepository.findOne(id);
	}

	@Override
	@Cacheable(key = "'dict_' + #type", unless = "#result eq null")
	public List<Dict> getDictList(String type) {
		List<Dict> dictList = new ArrayList<Dict>();
		if (StringHelper.isBlank(type)) return dictList;

		QDict qDict = QDict.dict;
		return this.queryFactory.selectFrom(qDict).where(qDict.type.eq(type.trim())).fetch();
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Dict saveOrUpdate(Dict dict) {
		if (null == dict) return null;

		Dict dbDict = this.dictRepository.saveAndFlush(dict);

		return dbDict;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean delById(Integer id) {
		if (null == id || id <= 0) return Boolean.FALSE;

		QDict qDict = QDict.dict;
		long num = this.queryFactory.update(qDict).set(qDict.statu, 1) // 0 正常 1删除
				.where(qDict.id.eq(id.intValue()))
				.execute();

		return num > 0;
	}
}

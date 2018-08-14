package com.github.liuweijw.business.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.beans.CompanyForm;
import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.Company;
import com.github.liuweijw.business.admin.domain.QCompany;
import com.github.liuweijw.business.admin.repository.CompanyRepository;
import com.github.liuweijw.business.admin.service.CompanyService;
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.commons.utils.RandomHelper;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Component
@CacheConfig(cacheNames = AdminCacheKey.COMPANY_INFO)
public class CompanyServiceImpl extends JPAFactoryImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	// @Autowired
	// private IdWorkerService idWorkerService;

	@Override
	@Cacheable(key = "'company_code' + #p0", unless = "#result eq null")
	public Company findByCode(String code) {
		if (PublicHelper.isEmpty(code)) return null;

		QCompany qCompany = QCompany.company;
		return this.queryFactory.selectFrom(qCompany)
				.where(qCompany.code.eq(code))
				.fetchFirst();
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public R<Boolean> add(Company infoModel) {
		if (PublicHelper.isEmpty(infoModel.getName()))
			return new R<Boolean>().data(false).failure("单位名称不能为空!");

		infoModel.setId(null);
		infoModel.setCode(RandomHelper.randomStringUpper());
		infoModel.setCtime(new Date());
		companyRepository.save(infoModel);

		return new R<Boolean>().data(true).success();
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public R<Boolean> upd(Company infoModel) {
		if (PublicHelper.isEmpty(infoModel.getCode()))
			return new R<Boolean>().data(false).failure("单位编码不能为空!");

		Company dbModel = this.findByCode(infoModel.getCode());
		if (null == dbModel)
			return new R<Boolean>().data(false).failure("单位不存在!");

		dbModel.setName(infoModel.getName());
		dbModel.setStatu(infoModel.getStatu());
		companyRepository.saveAndFlush(dbModel);

		return new R<Boolean>().data(true).success();
	}

	@Override
	@Cacheable(key = "'page_company_' + #p0.currentPage + '_' + #p0.pageSize + '_' + #p0.name")
	public PageBean<Company> findAll(CompanyForm infoModel) {
		QCompany qCompany = QCompany.company;
		Predicate qNamePredicate = null;
		if (StringUtils.isNotBlank(infoModel.getName())) {
			qNamePredicate = qCompany.name.like("%" + infoModel.getName().trim() + "%");
		}
		Predicate predicate = ExpressionUtils.allOf(qNamePredicate);

		Sort sort = new Sort(Sort.Direction.DESC, "code");

		PageRequest pageRequest = PageUtils.of(infoModel, sort);
		Page<Company> pageList = companyRepository.findAll(predicate, pageRequest);

		return PageUtils.of(pageList);
	}

	@Override
	@Cacheable(key = "'company_list_all'", unless = "#result eq null")
	public List<Company> findAllList() {

		Sort sort = new Sort(Sort.Direction.DESC, "code");

		return companyRepository.findAll(sort);
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Boolean delByCode(String code) {
		if (PublicHelper.isEmpty(code)) return false;

		QCompany qCompany = QCompany.company;
		Long num = this.queryFactory.delete(qCompany)
				.where(qCompany.code.eq(code))
				.execute();

		return null != num && num > 0;
	}

}

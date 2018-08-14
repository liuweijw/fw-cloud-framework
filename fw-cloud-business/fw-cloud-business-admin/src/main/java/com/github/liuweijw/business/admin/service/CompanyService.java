package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.beans.CompanyForm;
import com.github.liuweijw.business.admin.domain.Company;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.base.page.PageBean;

public interface CompanyService {

	Company findByCode(String code);

	R<Boolean> add(Company infoModel);

	R<Boolean> upd(Company infoModel);

	PageBean<Company> findAll(CompanyForm queryModel);

	List<Company> findAllList();

	Boolean delByCode(String code);

}

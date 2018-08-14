package com.github.liuweijw.business.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.beans.CompanyForm;
import com.github.liuweijw.business.admin.domain.Company;
import com.github.liuweijw.business.admin.service.CompanyService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.base.page.PageBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 单位管理服务
 *
 * @author liuweijw
 */
@RestController
@Api(value = "单位信息")
@RequestMapping("/company")
@PrePermissions(value = Module.COMPANY, required = false)
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "查询", notes = "查询所有单位数据")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<List<Company>> listAll(HttpServletRequest request) {
		List<Company> allList = this.companyService.findAllList();
		return new R<List<Company>>().data(allList).success();
	}

	@ApiOperation(value = "查询", notes = "单位列表")
	@ApiImplicitParam(name = "queryModel", value = "单位分页查询", dataType = "CompanyForm")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<Company>> list(HttpServletRequest request, @RequestBody CompanyForm queryModel) {
		PageBean<Company> pageData = this.companyService.findAll(queryModel);
		return new R<PageBean<Company>>().data(pageData).success();
	}

	@ApiOperation(value = "新增", notes = "新增单位信息")
	@ApiImplicitParam(name = "infoModel", dataType = "Company")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> add(HttpServletRequest request, @RequestBody Company infoModel) {
		return this.companyService.add(infoModel);
	}

	@ApiOperation(value = "更新", notes = "单位数据更新")
	@ApiImplicitParam(name = "infoModel", dataType = "Company")
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> upd(HttpServletRequest request, @RequestBody Company infoModel) {
		return this.companyService.upd(infoModel);
	}

	@ApiOperation(value = "删除", notes = "根据单位编码删除")
	@ApiImplicitParam(name = "code", value = "单位编码", required = true, dataType = "string", paramType = "path")
	@RequestMapping(value = "/del/{code}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> delByMembercode(HttpServletRequest request, @PathVariable("code") String code) {
		return new R<Boolean>().data(this.companyService.delByCode(code));
	}

}

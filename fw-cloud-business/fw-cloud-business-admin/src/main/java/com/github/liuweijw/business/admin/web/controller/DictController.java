package com.github.liuweijw.business.admin.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.domain.Dict;
import com.github.liuweijw.business.admin.service.DictService;
import com.github.liuweijw.business.commons.permission.Functional;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.core.configuration.ApiTag;

/**
 * 字典管理
 * 
 * @author liuweijw
 */
@Api(value = "字典信息管理", tags = ApiTag.TAG_DEFAULT)
@RestController
@RequestMapping("/dict")
@PrePermissions(value = Module.DICT)
public class DictController extends BaseController {

	@Autowired
	private DictService	dictService;

	@ApiOperation(value = "查询", notes = "字典分页数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dict", value = "字典实体dict", required = true, dataType = "Dict"),
			@ApiImplicitParam(name = "pageParams", value = "分页pageParams", required = true, dataType = "PageParams") })
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PrePermissions(value = Functional.VIEW)
	public R<PageBean<Dict>> list(HttpServletRequest request, Dict dict, PageParams pageParams) {
		PageBean<Dict> pageData = this.dictService.findAll(pageParams, dict);
		return new R<PageBean<Dict>>().data(pageData);
	}

	@ApiOperation(value = "查询", notes = "根据id查询字典数据")
	@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int", paramType = "path")
	@GetMapping("/{id}")
	@PrePermissions(value = Functional.VIEW)
	public R<Dict> findDictById(@PathVariable Integer id) {
		return new R<Dict>().data(dictService.findById(id));
	}

	@ApiOperation(value = "查询", notes = "根据type查询字典数据")
	@ApiImplicitParam(name = "type", value = "", required = true, dataType = "string", paramType = "path")
	@GetMapping("/type/{type}")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Dict>> findDictByType(@PathVariable String type) {
		return new R<List<Dict>>().data(dictService.getDictList(type));
	}

	@ApiOperation(value = "查询", notes = "查询字典列表数据")
	@GetMapping("/typeList")
	@PrePermissions(value = Functional.VIEW)
	public R<List<Dict>> findDictList() {
		return new R<List<Dict>>().data(dictService.getAllList());
	}

	@ApiOperation(value = "新增", notes = "字典类别", produces = "application/json")
	@ApiImplicitParam(name = "dict", value = "", required = true, dataType = "Dict")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@PrePermissions(value = Functional.ADD)
	public R<Boolean> add(HttpServletRequest request, @RequestBody Dict dict) {
		if (null == dict) return new R<Boolean>().failure("字典信息为空");
		Dict updateDict = dictService.saveOrUpdate(dict);
		return new R<Boolean>().data(null != updateDict);
	}

	@ApiOperation(value = "修改", notes = "字典类别", produces = "application/json")
	@ApiImplicitParam(name = "dict", value = "", required = true, dataType = "Dict")
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	@PrePermissions(value = Functional.UPD)
	public R<Boolean> upd(HttpServletRequest request, @RequestBody Dict dict) {
		if (null == dict || null == dict.getId() || dict.getId() <= 0)
			return new R<Boolean>().failure("字典信息为空");
		Dict updateDict = dictService.saveOrUpdate(dict);
		return new R<Boolean>().data(null != updateDict);
	}

	@ApiOperation(value = "删除", notes = "字典类别")
	@ApiImplicitParam(name = "id", value = "", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	@PrePermissions(value = Functional.DEL)
	public R<Boolean> del(HttpServletRequest request, @PathVariable Integer id) {
		if (null == id || id.intValue() <= 0) return new R<Boolean>().failure("字典id为空");
		boolean isDel = dictService.delById(id);
		return new R<Boolean>().data(isDel);
	}
}

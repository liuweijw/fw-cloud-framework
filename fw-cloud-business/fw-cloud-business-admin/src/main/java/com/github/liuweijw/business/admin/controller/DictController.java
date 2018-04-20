package com.github.liuweijw.business.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.domain.Dict;
import com.github.liuweijw.business.admin.service.DictService;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.utils.R;

@RestController
@RequestMapping("/dict")
@PrePermissions(value = Module.DICT)
public class DictController extends BaseController {

	@Autowired
	private DictService	dictService;

	@GetMapping("/{id}")
	public R<Dict> dict(@PathVariable Integer id) {
		return new R<Dict>().data(dictService.findById(id));
	}

	/**
	 * 通过字典类型查找字典
	 * 
	 * @param type
	 *            类型
	 */
	@GetMapping("/type/{type}")
	public R<List<Dict>> findDictByType(@PathVariable String type) {
		return new R<List<Dict>>().data(dictService.getDictList(type));
	}

}

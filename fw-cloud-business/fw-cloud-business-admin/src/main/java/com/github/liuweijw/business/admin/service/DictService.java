package com.github.liuweijw.business.admin.service;

import java.util.List;

import com.github.liuweijw.business.admin.domain.Dict;

public interface DictService {

	/**
	 * 得到字典列表
	 */
	List<Dict> getAllList();

	/**
	 * 通过id 获取字典
	 */
	Dict findById(Integer id);

	/**
	 * 通过type 获取字典
	 */
	List<Dict> getDictList(String type);

}

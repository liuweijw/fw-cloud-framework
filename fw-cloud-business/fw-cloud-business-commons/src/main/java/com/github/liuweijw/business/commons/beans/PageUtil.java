package com.github.liuweijw.business.commons.beans;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 分页工具
 * 
 * @author liuweijw
 */
public class PageUtil {

	public static <T> PageBean<T> of(Page<T> pageList) {
		PageBean<T> pageData = new PageBean<T>();
		pageData.setCurrentPage(pageList.getNumber() + 1);
		pageData.setPageSize(pageList.getSize());
		pageData.setTotal(pageList.getTotalElements());
		pageData.setList(pageList.getContent());
		return pageData;
	}

	public static PageRequest of(PageParams pageParams, Sort sort) {
		return new PageRequest(pageParams.getCurrentPage() - 1, pageParams.getPageSize(), sort);
	}

}

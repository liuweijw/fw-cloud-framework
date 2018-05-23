package com.github.liuweijw.business.commons.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;

/**
 * 分页工具 注意分页是从0开始的
 * 
 * @author liuweijw
 */
public class PageUtils {

	public static <T> PageBean<T> of(List<T> resultList, Long total, Integer currentPage,
			Integer pageSize) {
		PageBean<T> pageData = new PageBean<T>();
		pageData.setCurrentPage(currentPage + 1);
		pageData.setPageSize(pageSize);
		pageData.setTotal(total);
		pageData.setList(resultList);
		return pageData;
	}

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

	public static PageRequest of(PageParams pageParams) {
		return new PageRequest(pageParams.getCurrentPage() - 1, pageParams.getPageSize());
	}

}

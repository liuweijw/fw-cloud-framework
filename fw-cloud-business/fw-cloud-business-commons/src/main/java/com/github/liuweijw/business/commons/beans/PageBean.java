package com.github.liuweijw.business.commons.beans;

import java.util.List;

/**
 * 分页逻辑
 * 
 * @author liuweijw
 * @param <T>
 */
public class PageBean<T> {

	/**
	 * 总记录数
	 */
	private Long	total		= 0l;

	/**
	 * 当前页码
	 */
	private Integer	currentPage	= 0;

	/**
	 * 每页多少条 limit
	 */
	private Integer	pageSize	= 0;

	private List<T>	list;

	public PageBean() {

	}

	public PageBean(Integer currentPage, Integer pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getCurrentPage() {
		currentPage = (null == currentPage || currentPage <= 0) ? 1 : currentPage;
		return currentPage - 1;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}

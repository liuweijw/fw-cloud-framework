package com.github.liuweijw.business.commons.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.github.liuweijw.business.commons.constants.PageConstant;

/**
 * page 查询参数封装
 * 
 * @author liuweijw
 */
@ApiModel(description = "分页查询表单")
public class PageParams {

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页码")
	private Integer	currentPage;

	/**
	 * 每页多少条 limit
	 */
	@ApiModelProperty(value = "每页多少条")
	private Integer	pageSize;

	public Integer getCurrentPage() {
		currentPage = (null == currentPage || currentPage <= 0) ? 1 : currentPage;
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return (null == pageSize || pageSize < 0) ? PageConstant.PAGE_NUM : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

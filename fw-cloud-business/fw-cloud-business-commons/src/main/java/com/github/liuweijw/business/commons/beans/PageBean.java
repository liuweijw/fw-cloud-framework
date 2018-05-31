package com.github.liuweijw.business.commons.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页逻辑
 * 
 * @author liuweijw
 * @param <T>
 */
@ApiModel(description = "分页数据Bean")
public class PageBean<T> implements Serializable {

	private static final long	serialVersionUID	= 2981460239669645039L;

	/**
	 * 总记录数
	 */
	@ApiModelProperty(value = "总记录数")
	private Long				total				= 0l;

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页码")
	private Integer				currentPage			= 0;

	/**
	 * 每页多少条 limit
	 */
	@ApiModelProperty(value = "每页多少条")
	private Integer				pageSize			= 0;

	// redis jackson list 必须是ArrayList类型，如果是java.util.Collections$UnmodifiableRandomAccessList将会报错
	@ApiModelProperty(value = "列表数据")
	private List<T>				list;

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
		return currentPage;
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

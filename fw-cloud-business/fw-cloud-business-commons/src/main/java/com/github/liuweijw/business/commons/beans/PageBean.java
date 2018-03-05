package com.github.liuweijw.business.commons.beans;

import java.util.List;

/**
 * 分页逻辑
 * 
 * @author liuweijw
 *
 * @param <T>
 */
public class PageBean<T> {

	/**
	 * 总记录数
	 */
	private Long total = 0l;
	
	/**
	 * 当前页码
	 */
	private Integer pageNo = 0;
	
	/**
	 * 每页多少条 limit
	 */
	private Integer pageNum = 0;
	
	private List<T> list;
	
	public PageBean(){
		
	}
	
	public PageBean(Integer pageNo, Integer pageNum) {
		this.pageNo = pageNo;
		this.pageNum = pageNum;
	}
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}

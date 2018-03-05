package com.github.liuweijw.business.commons.beans;

import com.github.liuweijw.business.commons.constants.PageConstant;

public class PageParams {

	/**
	 * 当前页码
	 */
	private Integer pageNo;
	
	/**
	 * 每页多少条 limit
	 */
	private Integer pageNum;
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public Integer getPageNo() {
		return (null == pageNo || pageNo < 0) ? 0 : pageNo;
	}

	public Integer getPageNum() {
		return (null == pageNum || pageNum < 0) ? PageConstant.PAGE_NUM : pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}

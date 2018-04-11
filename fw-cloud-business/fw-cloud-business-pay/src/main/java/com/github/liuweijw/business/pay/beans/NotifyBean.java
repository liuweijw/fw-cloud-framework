package com.github.liuweijw.business.pay.beans;

import java.io.Serializable;

/**
 * 回调通知Bean
 * 
 * @author liuweijw
 */
public class NotifyBean implements Serializable {

	private static final long	serialVersionUID	= -235116261615512831L;

	private String				method;

	private String				url;

	private String				orderId;

	private Integer				count;

	private Long				createTime;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}

package com.github.liuweijw.business.wechat.beans;

import java.io.Serializable;

/**
 * 缓存URLInfo
 * 
 * @author liuweijw
 */
public class UrlInfoBean implements Serializable {

	private static final long	serialVersionUID	= -2186777819702378053L;

	private String				uuid;

	private String				url;

	public UrlInfoBean() {

	}

	public UrlInfoBean(String uuid, String url) {
		this.uuid = uuid;
		this.url = url;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package com.github.liuweijw.core.beans.system;

import java.io.Serializable;

public class AuthPermission implements Serializable {

	private static final long	serialVersionUID	= 4566420419542436770L;

	/**
	 * 请求URL
	 */
	private String				url;

	public AuthPermission() {

	}

	public AuthPermission(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package com.github.liuweijw.business.wechat.beans;

public class UrlMemory {

	private String	url;

	private long	time;

	public boolean isExpire() {
		// 大于五分钟的将设置为过期
		return System.currentTimeMillis() - time > 300000;
	}

	public UrlMemory(String url) {
		this.url = url;
		this.time = System.currentTimeMillis();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}

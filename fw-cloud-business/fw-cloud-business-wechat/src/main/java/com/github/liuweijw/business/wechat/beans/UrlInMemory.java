package com.github.liuweijw.business.wechat.beans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlInMemory {

	private static Map<String, UrlMemory>	urls;

	private static UrlInMemory				instance	= new UrlInMemory();

	private UrlInMemory() {
		urls = new ConcurrentHashMap<String, UrlMemory>();
	}

	public static UrlInMemory getInstance() {
		return instance;
	}

	public Map<String, UrlMemory> getUrls() {
		return urls;
	}

	public String getAndRemoveMemoryUrl(String key) {
		UrlMemory urlMemory = urls.get(key);
		String url = null;
		if (null != urlMemory) {
			url = urlMemory.getUrl();
			urls.remove(key);
		}
		return url;
	}
}

package com.github.liuweijw.business.wechat.service;

import com.github.liuweijw.business.wechat.beans.UrlInfoBean;
import com.github.liuweijw.business.wechat.domain.UrlInfo;

public interface UrlInfoService {

	UrlInfo findByUuid(String uuid);

	UrlInfo saveOrUpdate(UrlInfo urlInfo);

	UrlInfoBean findFromCacheByUuid(String uuid);

	void cacheUrlInfo(UrlInfoBean urlInfoBean);

	long clearAllExpire();
}

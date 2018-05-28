package com.github.liuweijw.business.wechat.service;

import com.github.liuweijw.business.wechat.domain.UrlInfo;

public interface UrlInfoService {

	UrlInfo findByUuid(String uuid);

	UrlInfo saveOrUpdate(UrlInfo urlInfo);

	long clearAllExpire();
}

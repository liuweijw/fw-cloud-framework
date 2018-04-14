package com.github.liuweijw.business.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.wechat.domain.QUrlInfo;
import com.github.liuweijw.business.wechat.domain.UrlInfo;
import com.github.liuweijw.business.wechat.repository.UrlInfoRepository;
import com.github.liuweijw.core.utils.StringHelper;

@Component
public class UrlInfoServiceImpl extends JPAFactoryImpl implements UrlInfoService {

	@Autowired
	private UrlInfoRepository	urlInfoRepository;

	@Override
	public UrlInfo findByUuid(String uuid) {
		if (StringHelper.isBlank(uuid)) return null;

		QUrlInfo qUrlInfo = QUrlInfo.urlInfo;
		return this.queryFactory.selectFrom(qUrlInfo).where(qUrlInfo.uuid.eq(uuid)).fetchFirst();
	}

	@Override
	public UrlInfo saveOrUpdate(UrlInfo urlInfo) {
		if (null == urlInfo) return null;

		return urlInfoRepository.saveAndFlush(urlInfo);
	}

	@Override
	public boolean clearAll() {
		urlInfoRepository.deleteAllInBatch();
		return true;
	}

}

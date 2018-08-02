package com.github.liuweijw.business.wechat.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.wechat.domain.QWechatInfo;
import com.github.liuweijw.business.wechat.domain.WechatInfo;
import com.github.liuweijw.commons.utils.StringHelper;

@CacheConfig(cacheNames = "wechat_info_")
@Component
public class WechatInfoServiceImpl extends JPAFactoryImpl implements WechatInfoService {

	@Override
	@Cacheable(key = "#wechatId")
	public WechatInfo findByWechatId(String wechatId) {
		if (StringHelper.isBlank(wechatId)) return null;

		QWechatInfo qWechatInfo = QWechatInfo.wechatInfo;
		return this.queryFactory.selectFrom(qWechatInfo)
				.where(qWechatInfo.wechatId.eq(wechatId.trim()))
				.where(qWechatInfo.statu.eq(1))
				.fetchFirst();
	}

}

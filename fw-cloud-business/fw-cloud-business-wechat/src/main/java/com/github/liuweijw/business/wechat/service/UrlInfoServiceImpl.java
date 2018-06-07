package com.github.liuweijw.business.wechat.service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.wechat.beans.UrlInfoBean;
import com.github.liuweijw.business.wechat.domain.QUrlInfo;
import com.github.liuweijw.business.wechat.domain.UrlInfo;
import com.github.liuweijw.business.wechat.repository.UrlInfoRepository;
import com.github.liuweijw.commons.utils.StringHelper;

@Slf4j
@Component
public class UrlInfoServiceImpl extends JPAFactoryImpl implements UrlInfoService {

	@Autowired
	private UrlInfoRepository	urlInfoRepository;

	@Autowired
	private CacheManager		cacheManager;

	private static final String	WECHAT_URL_INFO	= "wechat_url_info_";

	@Override
	public UrlInfo findByUuid(String uuid) {
		if (StringHelper.isBlank(uuid)) return null;

		QUrlInfo qUrlInfo = QUrlInfo.urlInfo;
		UrlInfo dbUrlInfo = this.queryFactory.selectFrom(qUrlInfo).where(qUrlInfo.uuid.eq(uuid))
				.fetchFirst();

		return dbUrlInfo;
	}

	@Override
	public UrlInfoBean findFromCacheByUuid(String uuid) {
		log.info("从缓存查询微信UrlInfo[uuid]:" + uuid);
		Cache cache = cacheManager.getCache(WECHAT_URL_INFO);
		Optional<UrlInfoBean> optional = Optional.ofNullable(cache.get(uuid, UrlInfoBean.class));
		if (optional.isPresent()) {
			log.info("从缓存查询微信UrlInfo[uuid]:" + uuid + "查询成功");
			cache.evict(uuid);
			return optional.get();
		}
		log.error("从缓存查询微信UrlInfo[uuid]:" + uuid + "未查询到");
		UrlInfo dbUserinfo = findByUuid(uuid);
		if (null == dbUserinfo) {
			log.error("从db查询微信UrlInfo[uuid]:" + uuid + "未查询到");
			return new UrlInfoBean();
		}
		log.info("从db查询微信UrlInfo[uuid]:" + uuid + "查询成功");
		return new UrlInfoBean(dbUserinfo.getUuid(), dbUserinfo.getUrl());
	}

	@Override
	public void cacheUrlInfo(UrlInfoBean urlInfoBean) {
		try {
			log.info("设置缓存微信UrlInfo[uuid]:" + urlInfoBean.getUuid());
			cacheManager.getCache(WECHAT_URL_INFO).put(urlInfoBean.getUuid(), urlInfoBean);
		} catch (Exception e) {
			log.info("设置缓存微信UrlInfo[uuid]:" + urlInfoBean.getUuid() + "失败，入库保障");
			UrlInfo urlInfo = new UrlInfo();
			urlInfo.setTime(System.currentTimeMillis());
			urlInfo.setUrl(urlInfoBean.getUrl());
			urlInfo.setUuid(urlInfoBean.getUuid());
			this.saveOrUpdate(urlInfo);
		}
	}

	@Override
	@Transactional
	public UrlInfo saveOrUpdate(UrlInfo urlInfo) {
		if (null == urlInfo) return null;

		return urlInfoRepository.saveAndFlush(urlInfo);
	}

	@Override
	@Transactional
	public long clearAllExpire() {
		QUrlInfo qUrlInfo = QUrlInfo.urlInfo;

		// 删除十分钟前的历史数据
		long beforeTime = System.currentTimeMillis() - 600000;
		return this.queryFactory.delete(qUrlInfo).where(qUrlInfo.time.longValue().lt(beforeTime))
				.execute();
	}

}

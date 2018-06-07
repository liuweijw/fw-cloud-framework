package com.github.liuweijw.business.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.wechat.domain.AuthInfo;
import com.github.liuweijw.business.wechat.domain.QAuthInfo;
import com.github.liuweijw.business.wechat.repository.AuthInfoRepository;
import com.github.liuweijw.commons.utils.StringHelper;

/**
 * 微信授权信息保存
 * 
 * @author liuweijw
 */
@Component
public class AuthInfoServiceImpl extends JPAFactoryImpl implements AuthInfoService {

	@Autowired
	private AuthInfoRepository	authInfoRepository;

	@Override
	public AuthInfo saveOrUpdate(AuthInfo authInfo) {
		if (null == authInfo) return null;

		return authInfoRepository.saveAndFlush(authInfo);
	}

	@Override
	public AuthInfo findByOpenIdAndWechatId(String openId, String wechatId) {
		if (StringHelper.isBlank(openId) || StringHelper.isBlank(wechatId)) return null;

		QAuthInfo qAuthInfo = QAuthInfo.authInfo;
		return this.queryFactory.selectFrom(qAuthInfo).where(qAuthInfo.openId.eq(openId.trim()))
				.where(qAuthInfo.wechatId.eq(wechatId.trim())).fetchFirst();
	}

}

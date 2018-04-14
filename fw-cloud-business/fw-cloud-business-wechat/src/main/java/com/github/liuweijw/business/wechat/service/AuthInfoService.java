package com.github.liuweijw.business.wechat.service;

import com.github.liuweijw.business.wechat.domain.AuthInfo;

public interface AuthInfoService {

	AuthInfo saveOrUpdate(AuthInfo authInfo);

	AuthInfo findByOpenIdAndWechatId(String openId, String wechatId);

}

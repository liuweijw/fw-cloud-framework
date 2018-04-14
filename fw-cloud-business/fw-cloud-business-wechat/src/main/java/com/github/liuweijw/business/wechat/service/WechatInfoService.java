package com.github.liuweijw.business.wechat.service;

import com.github.liuweijw.business.wechat.domain.WechatInfo;

public interface WechatInfoService {

	WechatInfo findByWechatId(String wechatId);

}

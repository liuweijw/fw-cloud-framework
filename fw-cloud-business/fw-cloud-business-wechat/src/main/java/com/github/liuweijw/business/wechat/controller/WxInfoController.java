package com.github.liuweijw.business.wechat.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.wechat.domain.AuthInfo;
import com.github.liuweijw.business.wechat.domain.WechatInfo;
import com.github.liuweijw.business.wechat.service.AuthInfoService;
import com.github.liuweijw.business.wechat.service.WechatInfoService;
import com.github.liuweijw.commons.pay.beans.HttpResult;

/**
 * 通过openId 获取用户信息
 * 
 * @author liuweijw
 */
@Slf4j
@RestController
@RequestMapping("/wechat/info")
public class WxInfoController {

	@Autowired
	private AuthInfoService		authInfoService;

	@Autowired
	private WechatInfoService	wechatInfoService;

	@RequestMapping(value = "/{wechatId}/{openId}")
	public HttpResult getWechatInfoByOpenId(@PathVariable("wechatId") String wechatId,
			@PathVariable("openId") String openId) {

		WechatInfo wechatInfo = wechatInfoService.findByWechatId(wechatId);
		if (null == wechatInfo) {
			log.error("公众号wechatId[" + wechatId + "]不存在");
			return new HttpResult().failure("公众号wechatId[" + wechatId + "]不存在");
		}

		AuthInfo authInfo = authInfoService.findByOpenIdAndWechatId(openId, wechatId);

		if (null == authInfo) {
			log.error("公众号openId[" + openId + "]不存在");
			return new HttpResult().failure("公众号openId[" + openId + "]不存在");
		}
		return new HttpResult().data(authInfo).success();
	}

}

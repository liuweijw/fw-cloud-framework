package com.github.liuweijw.business.wechat.listener;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.wechat.beans.WechatNotifyBean;
import com.github.liuweijw.business.wechat.domain.AuthInfo;
import com.github.liuweijw.business.wechat.service.AuthInfoService;
import com.github.liuweijw.business.wechat.utils.EmojiUtils;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;
import com.github.liuweijw.core.commons.constants.MqQueueConstant;

/**
 * 日志队列消息监听：消息对象必须是经过序列化操作的对象
 * 
 * @author liuweijw
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.WECHAT_QUEUE)
public class WechatRabbitListener {

	@Autowired
	private WxMpService		wxService;

	@Autowired
	private AuthInfoService	authInfoService;

	@RabbitHandler
	public void receive(WechatNotifyBean wechatNotifyBean) {
		long start = System.currentTimeMillis();
		log.info("【wxauth.wechatRabbit】:exec receive start|" + start);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wechatNotifyBean.getWxMpOAuth2AccessToken();
		if (null == wxMpOAuth2AccessToken || StringHelper.isBlank(wechatNotifyBean.getWechatId())
				|| StringHelper.isBlank(wxMpOAuth2AccessToken.getOpenId())) return;
		log.info("【wxauth.wechatRabbit】:exec start");
		try {
			WxMpUser wxMpUser = null;
			String openId = wxMpOAuth2AccessToken.getOpenId();
			AuthInfo authInfo = authInfoService.findByOpenIdAndWechatId(openId, wechatNotifyBean
					.getWechatId());
			if (null == authInfo // 大于2小时
					|| System.currentTimeMillis() - authInfo.getUpdateTime().getTime() > 7200000) {
				boolean isSopeBase = wechatNotifyBean.isSopeBase();
				if (isSopeBase) {
					log.info("【wxauth.openId】静默登录");
					wxMpUser = wxService.getUserService().userInfo(openId);
				} else {
					// refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
					String refreshToken = wxMpOAuth2AccessToken.getRefreshToken();
					wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
					log.info("【wxauth.openId】主动登录");
					// 拉取用户信息(需scope为 snsapi_userinfo)
					wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
				}

				if (null != wxMpUser) {
					if (null == authInfo) authInfo = new AuthInfo();
					authInfo.setOpenId(openId);
					authInfo.setWechatId(wechatNotifyBean.getWechatId());
					authInfo.setNickName(WebUtils.buildURLEncoder(EmojiUtils.toHtml(wxMpUser
							.getNickname())));
					authInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
					authInfo.setCity(wxMpUser.getCity());
					authInfo.setProvince(wxMpUser.getProvince());
					authInfo.setLanguage(wxMpUser.getLanguage());
					authInfo.setRemark(wxMpUser.getRemark());
					authInfo.setSexDesc(wxMpUser.getSexDesc());
					authInfo.setSex(wxMpUser.getSex());
					authInfo.setCountry(wxMpUser.getCountry());
					authInfo.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
					if (null == authInfo.getCreateTime()) authInfo.setCreateTime(new Date());
					authInfo.setUpdateTime(new Date());
					authInfoService.saveOrUpdate(authInfo);
				}
			}
			log.info("【wxauth.wechatRabbit】:openId|" + openId);
		} catch (WxErrorException ex) {
			ex.printStackTrace();
			log.info("【wxauth.wechatRabbit】exception:" + ex.getError().getErrorMsg());
		}
		log.info("【wxauth.wechatRabbit】:exec finished 耗时：" + (System.currentTimeMillis() - start));
	}
}

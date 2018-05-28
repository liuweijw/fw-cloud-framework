package com.github.liuweijw.business.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.liuweijw.business.commons.utils.WebUtils;
import com.github.liuweijw.business.wechat.beans.WechatNotifyBean;
import com.github.liuweijw.business.wechat.config.WechatMpProperties;
import com.github.liuweijw.business.wechat.domain.UrlInfo;
import com.github.liuweijw.business.wechat.service.AuthInfoService;
import com.github.liuweijw.business.wechat.service.UrlInfoService;
import com.github.liuweijw.core.commons.constants.MqQueueConstant;
import com.github.liuweijw.core.utils.R;
import com.github.liuweijw.core.utils.RandomHelper;
import com.github.liuweijw.core.utils.StringHelper;

/**
 * 微信公众号授权管理
 * 
 * @author liuweijw
 */
@Slf4j
@Controller
@RequestMapping("/wechat/auth")
public class WxAuthorizeController {

	@Autowired
	private WxMpService			wxService;

	@Autowired
	private UrlInfoService		urlInfoService;

	@Autowired
	private AuthInfoService		authInfoService;

	@Autowired
	private RabbitTemplate		rabbitTemplate;

	@Autowired
	private WechatMpProperties	properties;

	private static final String	OPENID	= "openId";

	// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request, @RequestParam("_backUrl") String _backUrl,
			Integer from, @RequestParam("wechatId") String wechatId) {
		from = null == from || from < 0 ? 0 : from;
		String returnUrl = properties.getAuthUrl() + "/wechat/auth/openId?wechatId=" + wechatId
				+ "&from=" + from;
		// from == 1 已经关注（静默登录） from == 0 通过网页（用户授权）
		_backUrl = WebUtils.buildURLParams(_backUrl, OPENID);
		String state = WebUtils.buildURLEncoder(_backUrl);
		if (state.length() > 112) { // 微信端最长允许128-16(#wechat_redirect),长度超过改为项目段链接标识替换
			UrlInfo urlInfo = new UrlInfo();
			urlInfo.setUuid(RandomHelper.randomStringUpper());
			urlInfo.setUrl(_backUrl);
			urlInfo.setTime(System.currentTimeMillis());
			state = urlInfo.getUuid();
			urlInfoService.saveOrUpdate(urlInfo);
			// state = RandomHelper.randomStringUpper();
			// UrlInMemory.getInstance().getUrls().put(state, new UrlMemory(_backUrl));
		}
		String redirectURL = wxService.oauth2buildAuthorizationUrl(returnUrl,
				from.intValue() == 1 ? WxConsts.OAuth2Scope.SNSAPI_BASE
						: WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
		log.info("【wxauth.authorize】redirect:" + redirectURL);
		return "redirect:" + redirectURL;
	}

	// 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
	@RequestMapping(value = "/openId", method = RequestMethod.GET)
	public String openId(HttpServletRequest request, @RequestParam("code") String code,
			@RequestParam("state") String state, @RequestParam("from") Integer from,
			@RequestParam("wechatId") String wechatId) {

		String openId = "";
		try {
			boolean isSopeBase = from.intValue() == 1;
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			openId = wxMpOAuth2AccessToken.getOpenId();

			log.info("【wxauth.openId】:state|" + state);
			// AuthInfo authInfo = authInfoService.findByOpenIdAndWechatId(openId, wechatId);
			// if (null == authInfo // 大于2小时
			// || System.currentTimeMillis() - authInfo.getUpdateTime().getTime() > 7200000) {
			// WxMpUser wxMpUser = null;
			// if (isSopeBase) {
			// log.info("【wxauth.openId】静默登录");
			// wxMpUser = wxService.getUserService().userInfo(openId);
			// } else {
			// // refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
			// String refreshToken = wxMpOAuth2AccessToken.getRefreshToken();
			// wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
			// log.info("【wxauth.openId】主动登录");
			// // 拉取用户信息(需scope为 snsapi_userinfo)
			// wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			// }
			// if (null != wxMpUser) {
			// openId = wxMpUser.getOpenId();
			// if (null == authInfo) authInfo = new AuthInfo();
			// authInfo.setOpenId(openId);
			// authInfo.setWechatId(wechatId);
			// authInfo.setNickName(WebUtils.buildURLEncoder(EmojiUtils.toHtml(wxMpUser
			// .getNickname())));
			// authInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
			// authInfo.setCity(wxMpUser.getCity());
			// authInfo.setProvince(wxMpUser.getProvince());
			// authInfo.setLanguage(wxMpUser.getLanguage());
			// authInfo.setRemark(wxMpUser.getRemark());
			// authInfo.setSexDesc(wxMpUser.getSexDesc());
			// authInfo.setSex(wxMpUser.getSex());
			// authInfo.setCountry(wxMpUser.getCountry());
			// authInfo.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
			// authInfo.setUpdateTime(new Date());
			// authInfoService.saveOrUpdate(authInfo);
			// }
			// }
			WechatNotifyBean wechatNotifyBean = new WechatNotifyBean();
			wechatNotifyBean.setSopeBase(isSopeBase);
			wechatNotifyBean.setWechatId(wechatId);
			wechatNotifyBean.setWxMpOAuth2AccessToken(wxMpOAuth2AccessToken);
			rabbitTemplate.convertAndSend(MqQueueConstant.WECHAT_QUEUE, wechatNotifyBean);

			log.info("【wxauth.openId】:openId|" + openId);
		} catch (WxErrorException ex) {
			ex.printStackTrace();
			log.info("【wxauth.openId】exception:" + ex.getError().getErrorMsg());
		}

		String returnUrl = "";
		if (state.length() == 32 && !state.startsWith("http")) { // key
			UrlInfo urlInfo = urlInfoService.findByUuid(state);
			returnUrl = urlInfo.getUrl();
			// returnUrl = UrlInMemory.getInstance().getAndRemoveMemoryUrl(state);
		}

		String redirectUrl = WebUtils.buildAppendURLParams(returnUrl, OPENID + "=" + openId);
		log.info("【wxauth.openId】:redirect|" + redirectUrl);
		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
	public R<WxMpOAuth2AccessToken> refreshToken(HttpServletRequest request,
			@RequestParam("refreshToken") String refreshToken) {
		if (StringHelper.isBlank(refreshToken))
			return new R<WxMpOAuth2AccessToken>().failure("请求参数[refreshToken]不存在！");

		log.info("【wxauth】:refreshToken|" + refreshToken);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
		try {
			wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
			return new R<WxMpOAuth2AccessToken>().data(wxMpOAuth2AccessToken).success();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return new R<WxMpOAuth2AccessToken>().failure("微信refreshToken刷新失败！");
	}
}

package com.github.liuweijw.business.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.liuweijw.business.commons.utils.WebUtils;
import com.github.liuweijw.business.wechat.config.WechatMpProperties;
import com.github.liuweijw.business.wechat.domain.AuthInfo;
import com.github.liuweijw.business.wechat.domain.UrlInfo;
import com.github.liuweijw.business.wechat.service.AuthInfoService;
import com.github.liuweijw.business.wechat.service.UrlInfoService;
import com.github.liuweijw.core.utils.RandomHelper;

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
	private WechatMpProperties	properties;

	private static final String	OPENID	= "openId";

	// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request, @RequestParam("_backUrl") String _backUrl,
			Integer from, @RequestParam("wechatId") String wechatId) {
		from = null == from || from < 0 ? 0 : from;
		// WebUtils.buildRequestBaseURL(request) +
		String returnUrl = properties.getAuthUrl() + "/wechat/auth/openId?wechatId=" + wechatId
				+ "&from=" + from;
		// from == 1 已经关注（静默登录） from == 0 通过网页（用户授权）
		_backUrl = WebUtils.buildURLParams(_backUrl, OPENID);
		String state = WebUtils.buildURLEncoder(_backUrl);
		if (state.length() > 120) { // 微信端最长允许128,长度超过改为项目段链接标识替换
			UrlInfo urlInfo = new UrlInfo();
			urlInfo.setUuid(RandomHelper.randomStringUpper());
			urlInfo.setUrl(_backUrl);
			state = urlInfo.getUuid();
			urlInfoService.saveOrUpdate(urlInfo);
		}
		String redirectURL = wxService.oauth2buildAuthorizationUrl(returnUrl,
				from.intValue() == 1 ? WxConsts.OAuth2Scope.SNSAPI_BASE
						: WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
		log.info("【wxauth微信网页静默授权URL】:" + redirectURL);
		return "redirect:" + redirectURL;
	}

	// 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
	@RequestMapping(value = "/openId", method = RequestMethod.GET)
	public String openId(HttpServletRequest request, @RequestParam("code") String code,
			@RequestParam("state") String state, @RequestParam("from") Integer from,
			@RequestParam("wechatId") String wechatId) {
		// 网页授权之后进来
		String openId = "";
		WxMpUser wxMpUser = null;
		try {
			boolean isSopeBase = from.intValue() == 1;

			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
			wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			openId = wxMpOAuth2AccessToken.getOpenId();

			if (isSopeBase) {
				log.info("【wxauth微信网页静默授权】:" + isSopeBase);
				wxMpUser = wxService.getUserService().userInfo(openId);
			} else {
				log.info("【wxauth微信网页主动授权】:" + !isSopeBase);
				// 刷新access_token
				// refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
				String refreshToken = wxMpOAuth2AccessToken.getRefreshToken();
				log.info("【wxauth微信网页主动授权】:refreshToken|" + refreshToken);
				wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(refreshToken);
				// 拉取用户信息(需scope为 snsapi_userinfo)
				wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			}

			if (null != wxMpUser) {
				openId = wxMpUser.getOpenId();
				log.info("【wxauth微信网页授权】:openId|" + openId);
				AuthInfo authInfo = authInfoService.findByOpenIdAndWechatId(openId, wechatId);
				authInfo = null == authInfo ? new AuthInfo() : authInfo;
				authInfo.setOpenId(openId);
				authInfo.setWechatId(wechatId);
				authInfo.setNickName(WebUtils.buildURLEncoder(wxMpUser.getNickname()));
				authInfo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
				authInfo.setCity(wxMpUser.getCity());
				authInfo.setProvince(wxMpUser.getProvince());
				authInfo.setLanguage(wxMpUser.getLanguage());
				authInfo.setRemark(wxMpUser.getRemark());
				authInfo.setSex(wxMpUser.getSex());
				authInfo.setCountry(wxMpUser.getCountry());
				log.info("【wxauth微信网页授权imgUrl】:" + wxMpUser.getHeadImgUrl());
				authInfoService.saveOrUpdate(authInfo);
			}

			log.info("【wxauth微信网页授权openId】:" + openId);
		} catch (WxErrorException ex) {
			ex.printStackTrace();
			log.info("【wxauth微信网页授权】:" + ex.getError().getErrorMsg());
		}

		if (state.length() == 32 && !state.startsWith("http")) { // key
			UrlInfo urlInfo = urlInfoService.findByUuid(state);
			state = urlInfo.getUrl();
		}

		String redirectUrl = WebUtils.buildAppendURLParams(state, OPENID + "=" + openId);
		log.info("【wxauth微信网页授权redirect】:" + redirectUrl);
		return "redirect:" + redirectUrl;
	}

}

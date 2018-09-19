package com.github.liuweijw.business.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.liuweijw.business.commons.utils.RequestUtil;
import com.github.liuweijw.business.wechat.beans.UrlInfoBean;
import com.github.liuweijw.business.wechat.beans.WechatNotifyBean;
import com.github.liuweijw.business.wechat.config.WechatMpProperties;
import com.github.liuweijw.business.wechat.service.UrlInfoService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.utils.RandomHelper;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;
import com.github.liuweijw.core.commons.constants.MqQueueConstant;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

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
	private RabbitTemplate		rabbitTemplate;

	@Autowired
	private WechatMpProperties	properties;

	// @Autowired
	// private TaskExecutor taskExecutor;

	private static final String	OPENID	= "openId";

	// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(HttpServletRequest request, @RequestParam("_backUrl") String _backUrl,
			Integer from, @RequestParam("wechatId") String wechatId, Long t) {
		long start = System.currentTimeMillis();
		log.info("【wxauth.authorize】_backUrl:" + _backUrl);
		from = null == from || from < 0 ? 0 : from;
		t = null == t || t < 0 ? 0 : t;
		String returnUrl = properties.getAuthUrl() + "/wechat/auth/openId?wechatId=" + wechatId
				+ "&from=" + from + "&t=" + t;
		// from == 1 已经关注（静默登录） from == 0 通过网页（用户授权）
		String state = WebUtils.buildURLEncoder(_backUrl);
		log.info("【wxauth.authorize】state:" + state);

		if (state.length() > 112) { // 微信端最长允许128-16(#wechat_redirect),长度超过改为项目段链接标识替换
			state = RandomHelper.randomStringUpper();
			UrlInfoBean urlInfoBean = new UrlInfoBean(state, _backUrl);
			urlInfoService.cacheUrlInfo(urlInfoBean);
		}
		String redirectURL = wxService.oauth2buildAuthorizationUrl(
				returnUrl,
				from.intValue() == 1 ? WxConsts.OAuth2Scope.SNSAPI_BASE
						: WxConsts.OAuth2Scope.SNSAPI_USERINFO,
				state);
		// 微信默认会发送两次回调请求问题处理 -设置之后还是一样问题暂未解决,目前调用服务端采用其它方式规避此问题
		// https://blog.csdn.net/jiangguilong2000/article/details/79416615
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=xxx&redirect_uri=https&response_type=code&scope=snsapi_base&state=xxx#wechat_redirect
		// if (!redirectURL.contains("connect_redirect=")) {
		// redirectURL = redirectURL.replace("#wechat_redirect",
		// "&connect_redirect=1#wechat_redirect");
		// }
		log.info("【wxauth.authorize】redirect:" + redirectURL);
		long end = System.currentTimeMillis();
		log.info("【wxauth.authorize】耗时:" + (end - start));
		log.info("【wxauth.authorize】请求从第三方应用到跳转授权开始[" + t + "],耗时:" + (end - t));
		return "redirect:" + redirectURL;
	}

	// 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
	@RequestMapping(value = "/openId", method = RequestMethod.GET)
	public String openId(HttpServletRequest request, @RequestParam("code") String code,
			@RequestParam("state") String state, @RequestParam("from") Integer from,
			@RequestParam("wechatId") String wechatId, @RequestParam("t") Long t) {

		long start = System.currentTimeMillis();
		String openId = "";
		try {
			boolean isSopeBase = from.intValue() == 1;
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			openId = wxMpOAuth2AccessToken.getOpenId();
			log.info("【wxauth.openId】:state|" + state);
			// 采用异步方式拉取用户信息
			// taskExecutor.execute(() -> {})
			long mqStart = System.currentTimeMillis();
			log.info("【wxauth.openId】发送MQ:" + mqStart);
			WechatNotifyBean wechatNotifyBean = new WechatNotifyBean();
			wechatNotifyBean.setSopeBase(isSopeBase);
			wechatNotifyBean.setWechatId(wechatId);
			wechatNotifyBean.setWxMpOAuth2AccessToken(wxMpOAuth2AccessToken);
			rabbitTemplate.convertAndSend(MqQueueConstant.WECHAT_QUEUE, wechatNotifyBean);
			log.info("【wxauth.openId】发送MQ耗时:" + (System.currentTimeMillis() - mqStart));
			log.info("【wxauth.openId】:openId|" + openId);
		} catch (WxErrorException ex) {
			ex.printStackTrace();
			log.info("【wxauth.openId】exception:" + ex.getError().getErrorMsg());
		}

		String returnUrl = "";
		if (state.length() == 32 && !state.startsWith("http")) { // key
			UrlInfoBean urlInfoBean = urlInfoService.findFromCacheByUuid(state);
			returnUrl = urlInfoBean.getUrl();
		} else {
			returnUrl = state;
		}

		String redirectUrl = RequestUtil.buildAppendURLParams(RequestUtil.buildURLParams(returnUrl, OPENID), OPENID + "=" + openId, "t=" + t);
		log.info("【wxauth.openId】:redirect|" + redirectUrl);
		long end = System.currentTimeMillis();
		log.info("【wxauth.openId】耗时:" + (end - start));
		log.info("【wxauth.authorize】请求从第三方应用到跳转授权开始[" + t + "],耗时:" + (end - t));
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

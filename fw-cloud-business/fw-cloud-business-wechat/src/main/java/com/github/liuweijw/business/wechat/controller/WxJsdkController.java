package com.github.liuweijw.business.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.liuweijw.commons.pay.beans.HttpResult;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;

/**
 * 获取微信jsdk签名信息
 * 
 * @author liuweijw
 */
@Slf4j
@Controller
@RequestMapping("/wechat/jsdk")
public class WxJsdkController {

	@Autowired
	private WxMpService	wxService;

	@RequestMapping(value = "/wechatParam")
	@ResponseBody
	public HttpResult wechatParam(@RequestParam("url") String url) {
		long start = System.currentTimeMillis();
		if (StringHelper.isBlank(url)) return new HttpResult().failure("url 参数验证失败！");

		String jsdkUrl = WebUtils.buildURLDecoder(url);
		try {
			WxJsapiSignature jsapi = wxService.createJsapiSignature(WebUtils
					.buildURLDecoder(jsdkUrl));
			log.info("[微信jsdk]，请求{}，获取耗时{}", jsdkUrl, (System.currentTimeMillis() - start));
			return new HttpResult().data(jsapi).success();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}

		return new HttpResult().failure("获取微信签名数据失败！");
	}

	// @RequestMapping(value = "/wechatTest")
	// @ResponseBody
	// public HttpResult wechatTest(HttpServletRequest request) {
	// try {
	// return new HttpResult().data(this.wxService.getAccessToken()).success();
	// } catch (WxErrorException e) {
	// e.printStackTrace();
	// }
	// return new HttpResult().failure("微信数据配置失败！");
	// }
}

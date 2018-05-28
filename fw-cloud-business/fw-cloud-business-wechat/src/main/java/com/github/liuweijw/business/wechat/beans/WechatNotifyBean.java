package com.github.liuweijw.business.wechat.beans;

import java.io.Serializable;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * 异步拉取微信登录信息
 * 
 * @author liuweijw
 */
public class WechatNotifyBean implements Serializable {

	private static final long		serialVersionUID	= 6492587838792423788L;

	private WxMpOAuth2AccessToken	wxMpOAuth2AccessToken;

	private String					wechatId;

	private boolean					isSopeBase;

	public WxMpOAuth2AccessToken getWxMpOAuth2AccessToken() {
		return wxMpOAuth2AccessToken;
	}

	public void setWxMpOAuth2AccessToken(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
		this.wxMpOAuth2AccessToken = wxMpOAuth2AccessToken;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public boolean isSopeBase() {
		return isSopeBase;
	}

	public void setSopeBase(boolean isSopeBase) {
		this.isSopeBase = isSopeBase;
	}

}

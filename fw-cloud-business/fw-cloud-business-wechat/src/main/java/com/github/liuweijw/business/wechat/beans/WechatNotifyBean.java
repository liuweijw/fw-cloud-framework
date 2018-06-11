package com.github.liuweijw.business.wechat.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * 异步拉取微信登录信息
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class WechatNotifyBean implements Serializable {

	private static final long		serialVersionUID	= 6492587838792423788L;

	private WxMpOAuth2AccessToken	wxMpOAuth2AccessToken;

	private String					wechatId;

	private boolean					isSopeBase;

}

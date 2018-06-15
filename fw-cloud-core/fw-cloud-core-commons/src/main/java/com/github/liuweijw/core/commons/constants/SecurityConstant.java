package com.github.liuweijw.core.commons.constants;

/**
 * @author liuweijw
 */
public interface SecurityConstant {

	/**
	 * 授权
	 */
	String	AUTHORIZATION						= "Authorization";

	/**
	 * clinet授权模式
	 */
	String	BASIC								= "Basic ";

	/**
	 * 授权码模式
	 */
	String	AUTHORIZATION_CODE					= "authorization_code";

	/**
	 * 密码模式
	 */
	String	PASSWORD							= "password";

	/**
	 * 刷新token
	 */
	String	REFRESH_TOKEN						= "refresh_token";

	/**
	 * oauth token
	 */
	String	OAUTH_TOKEN_URL						= "/oauth/token";

	/**
	 * 手机登录URL
	 */
	String	MOBILE_TOKEN_URL					= "/mobile/token";

	/**
	 * JWT 用户对象key
	 */
	String	JWT_USER_NAME						= "user_name";

	/**
	 * JWT 用户权限
	 */
	String	JWT_USER_AUTHORITIES				= "authorities";

	/**
	 * 默认的处理验证码的url前缀
	 */
	String	DEFAULT_VALIDATE_CODE_URL_PREFIX	= "/code";

	/**
	 * 手机号的处理验证码的url前缀
	 */
	String	MOBILE_VALIDATE_CODE_URL_PREFIX		= "/smsCode";

	/**
	 * 默认生成图形验证码宽度
	 */
	String	DEFAULT_IMAGE_WIDTH					= "150";

	/**
	 * 默认生成图像验证码高度
	 */
	String	DEFAULT_IMAGE_HEIGHT				= "32";

	/**
	 * 默认生成图形验证码长度
	 */
	String	DEFAULT_IMAGE_LENGTH				= "4";

	/**
	 * 默认生成图形验证码过期时间
	 */
	int		DEFAULT_IMAGE_EXPIRE				= 60;

	/**
	 * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
	 */
	String	DEFAULT_COLOR_FONT					= "black";

	/**
	 * 图片边框
	 */
	String	DEFAULT_IMAGE_BORDER				= "no";

	/**
	 * 默认图片间隔
	 */
	String	DEFAULT_CHAR_SPACE					= "5";

	/**
	 * 默认保存code的前缀
	 */
	String	DEFAULT_CODE_KEY					= "DEFAULT_CODE_KEY";

	/**
	 * 验证码文字大小
	 */
	String	DEFAULT_IMAGE_FONT_SIZE				= "30";

	/**
	 * Token-AuthUser
	 */
	String	TOKEN_USER_DETAIL					= "token-user-detail";

	/**
	 * 默认的social的登录地址
	 */
	String	DEFAULT_SOCIAL_PROCESS_URL			= "/social";

	/**
	 * 默认的social的注册地址
	 */
	String	DEFAULT_SOCIAL_SIGNUP_URL			= "/social/signup";

	/**
	 * 用户信息头
	 */
	String	USER_HEADER							= "request-user-header";

	/**
	 * 角色信息头
	 */
	String	ROLE_HEADER							= "request-role-header";

	/**
	 * LICENSE
	 */
	String	LICENSE								= "Copyright (c) 2018 liuweijw";

	/**
	 * 前缀
	 */
	String	PREFIX								= "liuweijw_";
}

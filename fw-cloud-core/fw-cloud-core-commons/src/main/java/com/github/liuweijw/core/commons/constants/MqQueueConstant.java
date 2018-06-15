package com.github.liuweijw.core.commons.constants;

/**
 * @author liuweijw MQ 消息队列
 */
public interface MqQueueConstant {

	/**
	 * log rabbit队列名称
	 */
	String	LOG_QUEUE				= "log";

	/**
	 * 发送短信验证码队列
	 */
	String	MOBILE_CODE_QUEUE		= "mobile_code_queue";

	/**
	 * 服务状态队列
	 */
	String	SERVICE_STATUS_CHANGE	= "service_status_change";

	/**
	 * wechat rabbit队列名称
	 */
	String	WECHAT_QUEUE			= "wechat";
}

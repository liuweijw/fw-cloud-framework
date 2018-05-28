package com.github.liuweijw.business.wechat.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liuweijw.core.commons.constants.MqQueueConstant;

/**
 * @author liuweijw rabbit初始化配置
 */
@Configuration
public class RabbitConfiguration {

	/**
	 * 初始化 log队列
	 *
	 * @return
	 */
	@Bean
	public Queue initWechatQueue() {
		return new Queue(MqQueueConstant.WECHAT_QUEUE);
	}

}

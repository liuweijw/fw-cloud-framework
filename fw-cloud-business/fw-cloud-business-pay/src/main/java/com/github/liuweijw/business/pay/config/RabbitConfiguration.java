package com.github.liuweijw.business.pay.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit初始化配置
 * 
 * @author liuweijw
 */
@Configuration
public class RabbitConfiguration {

	/**
	 * 发送到该队列的通知会在一段时间后过期进入到pay_notify_delay_quenu 每个通知可以控制自己的失效时间
	 */
	public final static String	PAY_NOTIFY_DELAY_PRE_MESSAGE_TTL	= "pay_notify_delay_pre_message_ttl";

	/**
	 * 发送到该队列的通知会在一段时间后过期进入到pay_notify_delay_quenu 队列里所有的通知都有统一的失效时间
	 */
	final static String			PAY_NOTIFY_DELAY_QUENU_ALL_TTL		= "pay_notify_delay_quenu_all_ttl";

	final static int			QUEUE_EXPIRATION					= 10000;								// 默认10秒

	/**
	 * 通知失效后进入的队列，也就是实际的消费队列
	 */
	public final static String	PAY_NOTIFY_DELAY_QUENU_NAME			= "pay_notify_delay_quenu";

	/**
	 * DLX
	 */
	final static String			PAY_NOTIFY_DELAY_EXCHANGE			= "pay_notify_delay_exchange";

	/**
	 * 路由到delay_queue_per_queue_ttl的exchange
	 */
	final static String			PAY_NOTIFY_PER_QUEUE_TTL_EXCHANGE	= "pay_notify_per_queue_ttl_exchange";

	/**
	 * 创建DLX exchange
	 */
	@Bean
	DirectExchange delayExchange() {
		return new DirectExchange(PAY_NOTIFY_DELAY_EXCHANGE);
	}

	/**
	 * 创建per_queue_ttl_exchange
	 */
	@Bean
	DirectExchange perQueueTTLExchange() {
		return new DirectExchange(PAY_NOTIFY_PER_QUEUE_TTL_EXCHANGE);
	}

	/**
	 * 创建delay_queue_per_message_ttl队列
	 */
	@Bean
	Queue delayQueuePerMessageTTL() {
		return QueueBuilder.durable(PAY_NOTIFY_DELAY_PRE_MESSAGE_TTL).withArgument(
				"x-dead-letter-exchange", PAY_NOTIFY_DELAY_EXCHANGE) // DLX，dead letter发送到的exchange
				.withArgument("x-dead-letter-routing-key", PAY_NOTIFY_DELAY_QUENU_NAME)
				// dead letter携带的routing key
				.build();
	}

	/**
	 * 创建delay_queue_per_queue_ttl队列
	 */
	@Bean
	Queue delayQueuePerQueueTTL() {
		return QueueBuilder.durable(PAY_NOTIFY_DELAY_QUENU_ALL_TTL).withArgument(
				"x-dead-letter-exchange", PAY_NOTIFY_DELAY_EXCHANGE) // DLX
				.withArgument("x-dead-letter-routing-key", PAY_NOTIFY_DELAY_QUENU_NAME)
				// dead letter携带的routing key
				.withArgument("x-message-ttl", QUEUE_EXPIRATION)
				// 设置队列的过期时间
				.build();
	}

	/**
	 * 创建pay_notify_delay_quenu队列，也就是实际消费队列
	 *
	 * @return
	 */
	@Bean
	Queue delayProcessQueue() {
		return QueueBuilder.durable(PAY_NOTIFY_DELAY_QUENU_NAME).build();
	}

	/**
	 * 将DLX绑定到实际消费队列
	 *
	 * @param delayProcessQueue
	 * @param delayExchange
	 * @return
	 */
	@Bean
	Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
		return BindingBuilder.bind(delayProcessQueue).to(delayExchange).with(
				PAY_NOTIFY_DELAY_QUENU_NAME);
	}

	/**
	 * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列
	 */
	@Bean
	Binding queueTTLBinding(Queue delayQueuePerQueueTTL, DirectExchange perQueueTTLExchange) {
		return BindingBuilder.bind(delayQueuePerQueueTTL).to(perQueueTTLExchange).with(
				PAY_NOTIFY_DELAY_QUENU_ALL_TTL);
	}

}

package com.github.liuweijw.business.pay.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.liuweijw.business.pay.beans.NotifyBean;
import com.github.liuweijw.business.pay.config.RabbitConfiguration;
import com.github.liuweijw.business.pay.service.NotifyService;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.commons.https.HttpUtil;

/**
 * 支付队列消息监听：消息对象必须是经过序列化操作的对象
 * 
 * @author liuweijw
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfiguration.PAY_NOTIFY_DELAY_QUENU_NAME)
public class NotifyRabbitListenter {

	@Autowired
	private PayOrderService	payOrderService;

	@Autowired
	private NotifyService	notifyService;

	private RestTemplate	restTemplate;

	@RabbitHandler
	public void receive(NotifyBean notifyBean) {
		if (null == notifyBean) return;
		String respUrl = notifyBean.getUrl();
		if (StringHelper.isBlank(respUrl)) {
			log.warn("notify url is empty. respUrl={}", respUrl);
			return;
		}

		String orderId = notifyBean.getOrderId();
		int count = null == notifyBean.getCount() ? 0 : notifyBean.getCount();

		try {
			log.info("==>MQ通知业务系统开始[orderId：{}][count：{}][time：{}]", orderId, count,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			restTemplate = HttpUtil.restTemplate(respUrl);
			String resultResponse = restTemplate.postForObject(respUrl, HttpEntity.EMPTY,
					String.class);
			log.info("==>MQ通知业务系统发生请求结果[{}]", resultResponse);

			// 验证结果
			log.info("notify response , OrderID={}", orderId);
			if (resultResponse.equalsIgnoreCase(PayConstant.RETURN_VALUE_SUCCESS)) {
				log.info("{} notify success, url:{}", notifyBean.getOrderId(), respUrl);
				// 修改订单表
				try {
					boolean result = payOrderService.updatePayOrderStatus4Complete(orderId);
					log.info("修改payOrderId={},订单状态为处理完成->{}", orderId, result ? "成功" : "失败");
				} catch (Exception e) {
					log.error("修改订单状态为处理完成异常" + e);
				}
				// 修改通知次数
				try {
					boolean result = payOrderService.updateNotify(orderId, 1);
					log.info("修改payOrderId={},通知业务系统次数->{}", orderId, result ? "成功" : "失败");
				} catch (Exception e) {
					log.error("修改通知次数异常" + e);
				}
				return; // 通知成功结束
			} else {
				// 通知失败，延时再通知
				int cnt = count + 1;
				log.info("notify count={}", cnt);
				// 修改通知次数
				try {
					boolean result = payOrderService.updateNotify(orderId, cnt);
					log.info("修改payOrderId={},通知业务系统次数->{}", orderId, result ? "成功" : "失败");
				} catch (Exception e) {
					log.error("修改通知次数异常" + e);
				}

				if (cnt > 5) {
					log.info("notify count>5 stop. url={}", respUrl);
					return;
				}

				notifyBean.setCount(cnt);
				this.notifyService.notifyPayOrder(notifyBean, cnt * 60 * 1000);
			}
			log.warn("notify failed. url:{}, response body:{}", respUrl, resultResponse);
		} catch (Exception e) {
			log.info("<==MQ通知业务系统结束[orderId：{}][count：{}][time：{}]", orderId, count,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			log.error("notify exception. url:" + respUrl + "|exception:" + e);
		}
	}
}

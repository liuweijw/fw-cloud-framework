package com.github.liuweijw.business.admin.listener;

import java.util.Date;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.admin.domain.LogInfo;
import com.github.liuweijw.business.admin.service.LogInfoService;
import com.github.liuweijw.core.beans.system.AuthLog;
import com.github.liuweijw.core.beans.system.Log;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.MqQueueConstant;

/**
 * 日志队列消息监听：消息对象必须是经过序列化操作的对象
 * 
 * @author liuweijw
 */
@Component
@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogRabbitListener {

	@Autowired
	private LogInfoService	logInfoService;

	@RabbitHandler
	public void receive(AuthLog authLog) {
		Log sysLog = authLog.getLog();
		MDC.put(CommonConstant.KEY_USER, authLog.getLog().getCreateBy());
		Date currentDate = new Date();
		if (null == sysLog.getCreateTime()) sysLog.setCreateTime(currentDate);
		if (null == sysLog.getUpdateTime()) sysLog.setUpdateTime(currentDate);
		LogInfo logInfo = new LogInfo();
		BeanUtils.copyProperties(sysLog, logInfo);
		logInfoService.saveOrUpdate(logInfo);
		MDC.remove(CommonConstant.KEY_USER);
	}
}

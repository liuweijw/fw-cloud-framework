package com.github.liuweijw.system.gateway.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.beans.system.AuthLog;
import com.github.liuweijw.core.beans.system.Log;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.MqQueueConstant;
import com.netflix.zuul.context.RequestContext;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.io.IoUtil;
import com.xiaoleilu.hutool.util.URLUtil;

/**
 * 日志异步实现
 * 
 * @author liuweijw
 */
@Component
public class LogServiceImpl implements LogService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void send(RequestContext requestContext) {
		HttpServletRequest request = requestContext.getRequest();
		String requestUri = request.getRequestURI();
		String method = request.getMethod();
		Log syslog = new Log();
		syslog.setType(CommonConstant.STATUS_NORMAL);
		syslog.setRemoteAddr(HttpUtil.getClientIP(request));
		syslog.setRequestUri(URLUtil.getPath(requestUri));
		syslog.setMethod(method);
		syslog.setUserAgent(request.getHeader("user-agent"));
		syslog.setParams(HttpUtil.toParams(request.getParameterMap()));
		Long startTime = (Long) requestContext.get("startTime");
		syslog.setTime(System.currentTimeMillis() - startTime);
		Date currentDate = new Date();
		syslog.setCreateTime(currentDate);
		syslog.setUpdateTime(currentDate);
		if (requestContext.get(CommonConstant.SERVICE_ID) != null) {
			syslog.setServiceId(requestContext.get(CommonConstant.SERVICE_ID).toString());
		}

		// 正常发送服务异常解析
		if (requestContext.getResponseStatusCode() != HttpStatus.SC_OK
				&& null != requestContext.getResponseDataStream()) {
			InputStream responseStream = requestContext.getResponseDataStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream inputStream = null;
			InputStream responseDataStream = null;
			byte[] buffer = IoUtil.readBytes(responseStream);
			try {
				baos.write(buffer);
				baos.flush();
				inputStream = new ByteArrayInputStream(baos.toByteArray());
				responseDataStream = new ByteArrayInputStream(baos.toByteArray());
				String response = IoUtil.read(inputStream, CommonConstant.UTF8);
				syslog.setType(CommonConstant.STATUS_LOCK);
				syslog.setException(response);
				requestContext.setResponseDataStream(responseDataStream);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IoUtil.close(responseDataStream);
				IoUtil.close(baos);
				IoUtil.close(responseStream);
			}
		}

		// 网关内部异常
		Throwable throwable = requestContext.getThrowable();
		if (throwable != null) {
			syslog.setException(throwable.getMessage());
		}

		AuthLog authLog = new AuthLog();
		// 保存发往MQ（只保存授权）
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && StringHelper.isNotBlank(authentication.getName())) {
			syslog.setCreateBy(authentication.getName());
			authLog.setLog(syslog);
			rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, authLog);
		}
	}

}

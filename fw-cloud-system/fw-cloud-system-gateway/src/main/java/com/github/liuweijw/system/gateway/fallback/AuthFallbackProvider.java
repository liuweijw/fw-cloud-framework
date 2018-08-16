package com.github.liuweijw.system.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.github.liuweijw.core.commons.constants.MessageConstant;
import com.github.liuweijw.core.commons.constants.ServiceIdConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * Auth 模块异常回调
 * 
 * @author liuweijw
 */
@Slf4j
@Component
public class AuthFallbackProvider implements FallbackProvider {

	@Override
	public String getRoute() {
		return ServiceIdConstant.AUTH_SERVICE;
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return fallbackResponse(null);
	}

	@Override
	public ClientHttpResponse fallbackResponse(Throwable cause) {

		return new ClientHttpResponse() {

			@Override
			public InputStream getBody() throws IOException {
				if (cause != null && cause.getMessage() != null) {
					log.error("调用:{} 异常：{}", getRoute(), cause.getMessage());
					return new ByteArrayInputStream(cause.getMessage().getBytes());
				} else {
					log.error("调用:{} 异常：{}", getRoute(), MessageConstant.SYSTEM_AUTH_NOTSUPPORT);
					return new ByteArrayInputStream(
							MessageConstant.SYSTEM_AUTH_NOTSUPPORT
									.getBytes());
				}
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.SERVICE_UNAVAILABLE;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.SERVICE_UNAVAILABLE.value();
			}

			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
			}

			@Override
			public void close() {

			}

		};
	}

}

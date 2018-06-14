package com.github.liuweijw.business.commons.web.aop;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.liuweijw.commons.base.R;

/**
 * 全局500异常
 * 
 * @author liuweijw
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public R<Exception> exception(Exception e) {
		log.info("全局异常信息: {}", e.getMessage(), e);
		return new R<Exception>().data(e).failure("全局异常信息");
	}

}

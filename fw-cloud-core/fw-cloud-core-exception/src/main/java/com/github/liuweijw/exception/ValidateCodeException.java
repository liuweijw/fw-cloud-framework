package com.github.liuweijw.exception;

/**
 * 验证码校验异常
 * 
 * @author liuweijw
 */
public class ValidateCodeException extends RuntimeException {

	private static final long	serialVersionUID	= 8798176161238427050L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}

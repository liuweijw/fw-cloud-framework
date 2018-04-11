package com.github.liuweijw.exception;

/**
 * 全局权限拒绝异常
 * 
 * @author liuweijw
 */
public class DeniedException extends RuntimeException {

	private static final long	serialVersionUID	= -72078024351770709L;

	public DeniedException() {
	}

	public DeniedException(String message) {
		super(message);
	}

	public DeniedException(Throwable cause) {
		super(cause);
	}

	public DeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

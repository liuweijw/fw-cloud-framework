package com.github.liuweijw.core.utils;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author liuweijw
 * 
 */
public class R<T> implements Serializable {

	private static final long serialVersionUID = -6043162591645086533L;

	private String msg = RConstant.SUCCESS_MSG;

	private int code = RConstant.SUCCESS;

    private T data;
    
    public R() {
        super();
    }
    
    public R<T> success() {
    	this.code = RConstant.SUCCESS;
		return this;
	}
    
    public R<T> success(String msg) {
    	this.code = RConstant.SUCCESS;
		this.msg = msg;
		return this;
	}
    
    public R<T> failure() {
    	this.code = RConstant.FAIL;
		return this;
	}
    
    public R<T> failure(String msg) {
    	this.code = RConstant.FAIL;
		this.msg = msg;
		return this;
	}
    
    public R<T> failure(Throwable e) {
    	this.msg = e.getMessage();
        this.code = RConstant.FAIL;
		return this;
	}
    
    public R<T> code(int code) {
		this.code = code;
		return this;
	}
    
    public R<T> data(T data) {
		this.data = data;
		return this;
	}

    public static R<String> of(final String msg, final int code) {
		return new R<String>().code(code).failure(msg);
	}
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

package com.github.liuweijw.core.beans.system;

import java.io.Serializable;

public class AuthLog implements Serializable {

	private static final long	serialVersionUID	= -7612739305546935933L;

	private Log					log;

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}

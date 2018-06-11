package com.github.liuweijw.core.beans.system;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthLog implements Serializable {

	private static final long	serialVersionUID	= -7612739305546935933L;

	private Log					log;

}

package com.github.liuweijw.business.wechat.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 缓存URLInfo
 * 
 * @author liuweijw
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlInfoBean implements Serializable {

	private static final long	serialVersionUID	= -2186777819702378053L;

	private String				uuid;

	private String				url;

}

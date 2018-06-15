package com.github.liuweijw.core.beans.system;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Log implements Serializable {

	private static final long	serialVersionUID	= 1378371285230828799L;

	private long				id;

	/**
	 * 日志类型
	 */
	private Integer				type;

	/**
	 * 日志标题
	 */
	private Integer				title;

	/**
	 * 服务ID
	 */
	private String				serviceId;

	/**
	 * 创建者
	 */
	private String				createBy;

	/**
	 * 操作IP地址
	 */
	private String				remoteAddr;

	/**
	 * 用户代理
	 */
	private String				userAgent;

	/**
	 * 请求URI
	 */
	private String				requestUri;

	/**
	 * 操作方式
	 */
	private String				method;

	/**
	 * 操作提交的数据
	 */
	private String				params;

	/**
	 * 执行时间
	 */
	private Long				time;

	/**
	 * 删除标记
	 */
	private Integer				statu				= 0;

	/**
	 * 异常信息
	 */
	private String				exception;

	/**
	 * 创建时间
	 */
	private Date				createTime;

	/**
	 * 更新时间
	 */
	private Date				updateTime;

}

package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志信息表
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = LogInfo.TABLE_NAME)
public class LogInfo implements Serializable {

	private static final long	serialVersionUID	= 6924830168001131974L;

	public static final String	TABLE_NAME			= "t_sys_log";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long				id;

	/**
	 * 日志类型
	 */
	@Column(name = "type")
	private Integer				type;

	/**
	 * 日志标题
	 */
	@Column(name = "title")
	private Integer				title;

	/**
	 * 服务ID
	 */
	@Column(name = "service_id")
	private String				serviceId;

	/**
	 * 创建者
	 */
	@Column(name = "create_by")
	private String				createBy;

	/**
	 * 操作IP地址
	 */
	@Column(name = "remote_addr")
	private String				remoteAddr;

	/**
	 * 用户代理
	 */
	@Column(name = "user_agent")
	private String				userAgent;

	/**
	 * 请求URI
	 */
	@Column(name = "request_uri")
	private String				requestUri;

	/**
	 * 操作方式
	 */
	@Column(name = "method")
	private String				method;

	/**
	 * 操作提交的数据
	 */
	@Column(name = "params")
	private String				params;

	/**
	 * 执行时间
	 */
	@Column(name = "time")
	private Long				time;

	/**
	 * 删除标记
	 */
	@Column(name = "statu")
	private Integer				statu				= 0;

	/**
	 * 异常信息
	 */
	@Column(name = "exception")
	private String				exception;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date				createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date				updateTime;

	/**
	 * id = 18 丢失精度适配
	 */
	private transient String	idView;

}

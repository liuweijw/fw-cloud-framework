package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 日志信息表
 * 
 * @author liuweijw
 */
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}

package com.github.liuweijw.business.wechat.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = UrlInfo.TABLE_NAME)
public class UrlInfo implements Serializable {

	private static final long	serialVersionUID	= 5262129387709185488L;

	public static final String	TABLE_NAME			= "t_wechat_url";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long				id;

	@Column(name = "uuid", length = 32, nullable = true)
	private String				uuid;

	@Column(name = "url", length = 510, nullable = true)
	private String				url;

	@Column(name = "time")
	private long				time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}

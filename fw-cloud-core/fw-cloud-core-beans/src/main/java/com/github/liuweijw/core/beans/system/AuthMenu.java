package com.github.liuweijw.core.beans.system;

import java.io.Serializable;

public class AuthMenu implements Serializable {

	private static final long	serialVersionUID	= 4566420419542436770L;

	/**
	 * 菜单id
	 */
	private Integer				menuId;
	/**
	 * 菜单名称
	 */
	private String				menuName;
	/**
	 * 请求路径
	 */
	private String				path;
	/**
	 * 请求链接
	 */
	private String				url;
	/**
	 * 父菜单ID
	 */
	private Integer				pid;
	/**
	 * 0-正常，1-删除
	 */
	private Integer				statu				= 0;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

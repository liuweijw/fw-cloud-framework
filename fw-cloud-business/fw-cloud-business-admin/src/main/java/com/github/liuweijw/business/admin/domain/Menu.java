package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuweijw
 */
@Entity
@Table(name = Menu.TABLE_NAME)
public class Menu implements Serializable {

	private static final long serialVersionUID = -7932712605092646920L;

	public static final String TABLE_NAME = "t_sys_menu";
	
	/**
     * 菜单ID
     */
	@Id
	@GeneratedValue
	@Column(name = "menu_id")
    private Integer menuId;
    /**
     * 菜单名称
     */
	@Column(name = "menu_name")
    private String menuName;
	/**
	 * 请求路径
	 */
	@Column(name = "path")
	private String path;
	/**
	 * 请求链接
	 */
	@Column(name = "url")
	private String url;
    /**
     * 父菜单ID
     */
	@Column(name = "pid")
    private Integer pid;
    /**
     * 创建时间
     */
	@Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
	@Column(name = "update_time")
    private Date updateTime;
	/**
     * 0-正常，1-删除
     */
    @Column(name = "statu")
    private Integer statu = 0;
    
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

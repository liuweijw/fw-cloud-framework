package com.github.liuweijw.admin.domain;

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
	@Column(name = "name")
    private String name;
    /**
     * 菜单权限标识
     */
	@Column(name = "permission")
    private String permission;
    /**
     * 请求链接
     */
	@Column(name = "url")
    private String url;
    /**
     * 请求方法
     */
	@Column(name = "method")
    private String method;
    /**
     * 父菜单ID
     */
	@Column(name = "parent_id")
    private Integer parentId;
    /**
     * 图标
     */
	@Column(name = "icon")
    private String icon;
    /**
     * VUE页面
     */
	@Column(name = "component")
    private String component;
    /**
     * 排序值
     */
	@Column(name = "sort")
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
	@Column(name = "type")
    private String type;
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
     * 0--正常 1--删除
     */
	@Column(name = "del_flag")
    private Integer delFlag = 0;
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}

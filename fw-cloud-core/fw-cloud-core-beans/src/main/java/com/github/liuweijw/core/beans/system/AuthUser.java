package com.github.liuweijw.core.beans.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthUser implements Serializable {

	private static final long serialVersionUID = 5350461830095965990L;
	
	/**
     * 用户名Id
     */
	private Integer userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 0-正常，1-删除
     */
    private Integer statu = 0;
    /**
     * 头像
     */
    private String picUrl;
    /**
     * 角色列表
     */
    private List<AuthRole> roleList = new ArrayList<AuthRole>();
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatu() {
		return statu;
	}
	public void setStatu(Integer statu) {
		this.statu = statu;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public List<AuthRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<AuthRole> roleList) {
		this.roleList = roleList;
	}

}

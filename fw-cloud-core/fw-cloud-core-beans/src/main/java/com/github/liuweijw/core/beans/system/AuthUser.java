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
    private Integer delFlag;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 部门Id
     */
    private Integer deptId;
    
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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<AuthRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AuthRole> roleList) {
		this.roleList = roleList;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}

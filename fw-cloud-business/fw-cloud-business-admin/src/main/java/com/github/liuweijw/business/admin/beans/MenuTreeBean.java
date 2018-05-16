package com.github.liuweijw.business.admin.beans;

import java.util.List;

import com.github.liuweijw.business.commons.tree.MenuTree;

public class MenuTreeBean {

	private List<MenuTree>	menuList;

	private String[]		permissions;

	public List<MenuTree> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuTree> menuList) {
		this.menuList = menuList;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

}

package com.github.liuweijw.business.commons.tree;

import com.github.liuweijw.core.beans.system.AuthMenu;

public class MenuTree extends TreeNode {

	private String	menuName;
	private String	url;
	private String	path;
	private Integer	statu;

	public MenuTree() {
	}

	public MenuTree(int id, int pid, String menuName) {
		this.id = id;
		this.pid = pid;
		this.menuName = menuName;
	}

	public MenuTree(int id, String menuName, MenuTree parent) {
		this.id = id;
		this.pid = parent.getId();
		this.menuName = menuName;
	}

	public MenuTree(AuthMenu authMenu) {
		this.id = authMenu.getMenuId();
		this.pid = authMenu.getPid();
		this.menuName = authMenu.getMenuName();
		this.url = authMenu.getUrl();
		this.path = authMenu.getPath();
		this.statu = authMenu.getStatu();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

}

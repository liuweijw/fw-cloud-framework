package com.github.liuweijw.business.commons.tree;

import com.github.liuweijw.core.beans.system.AuthMenu;

public class MenuTree extends TreeNode {

	private static final long	serialVersionUID	= 3878699444870356572L;

	private String				label;

	private String				path;

	private String				url;

	public MenuTree() {
	}

	public MenuTree(String id, String pid, String label) {
		this.id = id;
		this.pid = pid;
		this.label = label;
	}

	public MenuTree(AuthMenu authMenu) {
		this.id = authMenu.getMenuId() + "";
		this.pid = authMenu.getPid() + "";
		this.label = authMenu.getMenuName();
		this.url = authMenu.getUrl();
		this.path = authMenu.getPath();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

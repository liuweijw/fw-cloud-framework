package com.github.liuweijw.business.commons.tree;

import com.github.liuweijw.core.beans.system.AuthMenu;

public class MenuTree extends TreeNode {

	private String	label;

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
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}

package com.github.liuweijw.business.commons.tree;

import lombok.Getter;
import lombok.Setter;

import com.github.liuweijw.commons.base.tree.TreeNode;
import com.github.liuweijw.core.beans.system.AuthMenu;

@Setter
@Getter
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

}

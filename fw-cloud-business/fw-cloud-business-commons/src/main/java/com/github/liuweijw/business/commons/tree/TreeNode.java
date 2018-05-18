package com.github.liuweijw.business.commons.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形节点
 * 
 * @author liuweijw
 */
public class TreeNode implements Serializable {

	private static final long	serialVersionUID	= -8275451158911413692L;

	protected String			id;

	protected String			pid;

	protected List<TreeNode>	children			= new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}

package com.github.liuweijw.business.commons.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形节点
 * 
 * @author liuweijw
 *
 */
public class TreeNode {

	private int id;
	
	private int pid;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
        children.add(node);
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}

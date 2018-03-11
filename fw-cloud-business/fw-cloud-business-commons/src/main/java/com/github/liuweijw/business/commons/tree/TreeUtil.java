package com.github.liuweijw.business.commons.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归树形
 * 
 * @author liuweijw
 *
 */
public class TreeUtil {

    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getPid())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    private static <T extends TreeNode> T findChildren(T node, List<T> treeNodes) {
        for (T n : treeNodes) {
            if (node.getId() == n.getPid()) {
                if (node.getChildren() == null) {
                	node.setChildren(new ArrayList<TreeNode>());
                }
                node.add(findChildren(n, treeNodes));
            }
        }
        return node;
    }
    
}

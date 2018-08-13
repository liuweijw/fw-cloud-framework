package cloud.simple.service.util;

import java.util.ArrayList;
import java.util.List;

import cloud.simple.service.dto.TreeNode;

public class TreeBuilder {
  
    /** 
     * 使用递归方法建树 
     * @param treeNodes 
     * @return 
     */  
    public static List<TreeNode> buildByRecursive(List<TreeNode> treeNodes) {  
        List<TreeNode> trees = new ArrayList<TreeNode>();  
        for (TreeNode treeNode : treeNodes) {  
        	System.err.println(treeNode);
            if ("0".equals(treeNode.getParentId())) {  
                trees.add(findChildren(treeNode,treeNodes, 0));  
            }  
        }  
        return trees;  
    }  
  
    /** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    public static TreeNode findChildren(TreeNode treeNode,List<TreeNode> treeNodes, int root) {  
    	treeNode.put("selected", false);
    	treeNode.put("level", root);
        for (TreeNode it : treeNodes) {  
            if(treeNode.getId().equals(it.getParentId())) {  
                if (treeNode.getChildren() == null) {  
                    treeNode.setChildren(new ArrayList<TreeNode>());  
                }  
                treeNode.getChildren().add(findChildren(it,treeNodes, root+1));  
            }  
        }  
        return treeNode;  
    }
    
   /* public static void main(String[] args) {
    	TreeNode treeNode1 = new TreeNode("1","广州","0");  
        TreeNode treeNode2 = new TreeNode("2","深圳","0");  
  
        TreeNode treeNode3 = new TreeNode("3","天河区","1");  
        TreeNode treeNode4 = new TreeNode("4","越秀区","2");  
        TreeNode treeNode5 = new TreeNode("5","黄埔区",treeNode1);  
        TreeNode treeNode6 = new TreeNode("6","石牌",treeNode3);  
        TreeNode treeNode7 = new TreeNode("7","百脑汇",treeNode6);  
  
  
        TreeNode treeNode8 = new TreeNode("8","南山区",treeNode2);  
        TreeNode treeNode9 = new TreeNode("9","宝安区",treeNode2);  
        TreeNode treeNode10 = new TreeNode("10","科技园",treeNode8);  
  
  
        List<TreeNode> list = new ArrayList<TreeNode>();  
  
        list.add(treeNode1);  
        list.add(treeNode2);  
        list.add(treeNode3);  
        list.add(treeNode4);  
        list.add(treeNode5);  
        list.add(treeNode6);  
        list.add(treeNode7);  
        list.add(treeNode8);  
        list.add(treeNode9);  
        list.add(treeNode10);  
  
        List<TreeNode> trees_ = TreeBuilder.buildByRecursive(list);  
        
        System.out.println(trees_);
    }*/
}

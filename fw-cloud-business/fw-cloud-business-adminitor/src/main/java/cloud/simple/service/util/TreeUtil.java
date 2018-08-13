package cloud.simple.service.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cloud.simple.service.dto.TreeNode;
import cloud.simple.service.model.SysAdminMenu;
import cloud.simple.service.model.SysAdminRule;

/**
 * 树工具类
 * @author leo
 *
 */
public class TreeUtil {
	/**
     * 将数据集转换成Tree（真正的Tree结构）
     * @param array $list 要转换的数据集
     * @param string $root 返回的根节点ID
     * @return List<TreeNode>
     */
	public static List<TreeNode> listMenuToTree(List<SysAdminMenu> list,Integer rootId) {
		//创建tree
		List<TreeNode> tree = new ArrayList<TreeNode>();  
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysAdminMenu menu : list) {
				Integer pid = menu.getPid();
				if(pid == null || pid.equals(rootId)) {
					TreeNode treeNode = new TreeNode(menu.getId().toString(), menu.getTitle(),"0");
					treeNode.put("url", menu.getUrl());
					treeNode.put("menu_type", menu.getMenuType());
					treeNode.put("sort", menu.getSort());
					treeNode.put("status", menu.getStatus());
					treeNode.put("rule_id", menu.getRuleId());
					treeNode.put("module", menu.getModule());
					treeNode.put("module", menu.getModule());
					treeNode.put("menu", menu.getMenu());
					tree.add(treeNode);
				} else {
					TreeNode treeNode = new TreeNode(menu.getId().toString(), menu.getTitle(),pid.toString());
					treeNode.put("url", menu.getUrl());
					treeNode.put("menu_type", menu.getMenuType());
					treeNode.put("sort", menu.getSort());
					treeNode.put("status", menu.getStatus());
					treeNode.put("rule_id", menu.getRuleId());
					treeNode.put("module", menu.getModule());
					treeNode.put("module", menu.getModule());
					treeNode.put("menu", menu.getMenu());
					tree.add(treeNode);
				}
			}
		}
		return TreeBuilder.buildByRecursive(tree);
	}
	
	/**
     * 将数据集转换成Tree（真正的Tree结构）
     * @param array $list 要转换的数据集
     * @param string $root 返回的根节点ID
     * @return List<TreeNode>
     */
	public static List<TreeNode> listRuleToTree(List<SysAdminRule> list,Integer rootId) {
		//创建tree
		List<TreeNode> tree = new ArrayList<TreeNode>();  
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysAdminRule rule : list) {
				Integer pid = rule.getPid();
				if(pid == null || pid.equals(rootId)) {
					TreeNode treeNode = new TreeNode(rule.getId().toString(), rule.getTitle(),"0");
					tree.add(treeNode);
				} else {
					TreeNode treeNode = new TreeNode(rule.getId().toString(), rule.getTitle(),pid.toString());
					tree.add(treeNode);
				}
			}
		}
		return TreeBuilder.buildByRecursive(tree);
	}
}

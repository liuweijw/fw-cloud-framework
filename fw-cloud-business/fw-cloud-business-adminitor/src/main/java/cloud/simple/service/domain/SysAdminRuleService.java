package cloud.simple.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cloud.simple.service.base.BaseServiceImpl;
import cloud.simple.service.dao.SysAdminGroupDao;
import cloud.simple.service.dao.SysAdminRuleDao;
import cloud.simple.service.model.SysAdminGroup;
import cloud.simple.service.model.SysAdminRule;
import cloud.simple.service.util.BeanToMapUtil;
import cloud.simple.service.util.Category;
import tk.mybatis.mapper.common.Mapper;
@Service
public class SysAdminRuleService extends BaseServiceImpl<SysAdminRule>{
	
	@Autowired
	private SysAdminRuleDao sysAdminRuleDao;
	@Autowired
	private SysAdminGroupDao sysAdminGroupDao;
	
	@Override
	public Mapper<SysAdminRule> getMapper() {
		return sysAdminRuleDao;
	}
	
	/**
	 * 根据用户名获取rule数组
	 * @param userId 用户id
	 */
	public List<SysAdminRule> getTreeRuleByUserId(Integer userId) {
		List<SysAdminRule> rulesList = getRulesByUserId(userId);
		//处理树
		List<SysAdminRule> rulesTreeList = this.buildByRecursiveTree(rulesList);
		return rulesTreeList;
	}

	
	/** 
     * 使用递归方法建树 
     * @param rootSysAdminRule 原始的数据
     * @return 
     */  
	private List<SysAdminRule> buildByRecursiveTree(List<SysAdminRule> rootSysAdminRules){
	    List<SysAdminRule> trees = new ArrayList<SysAdminRule>();
	    for(SysAdminRule menu : rootSysAdminRules) {
	    	if ("0".equals(menu.getPid().toString())) {  
                trees.add(getChild(menu,rootSysAdminRules, 1));  
            }  
	    }
	    return trees;
	}

	/**
	 * 递归查找子菜单
	 * 
	 * @param treeMenu
	 *            当前菜单id
	 * @param treeNodes
	 *            要查找的列表
	 * @return
	 */
	private SysAdminRule getChild(SysAdminRule treeMenu, List<SysAdminRule> treeNodes, int level) {
		for (SysAdminRule it : treeNodes) {
			if (treeMenu.getId().equals(it.getPid())) {
				if (treeMenu.getChild() == null) {
					treeMenu.setChild(new ArrayList<>());
				}
				treeMenu.getChild().add(getChild(it, treeNodes, level + 1));
			}
		}
		return treeMenu;
	}
	/**
	 * 给树状规则表处理成 module-controller-action
	 * @return treeNodes
	 */
	public List<String> rulesDeal(List<SysAdminRule> treeNodes) {
		List<String> ruleStr = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(treeNodes)) {
			for (SysAdminRule root : treeNodes) {
				if (CollectionUtils.isNotEmpty(root.getChild())) {
					for (SysAdminRule c1 : root.getChild()) {
						if (CollectionUtils.isNotEmpty(c1.getChild())) {
							for (SysAdminRule c2 : c1.getChild()) {
								ruleStr.add(root.getName() + "-"  +c1.getName() + "-" + c2.getName());
							}
						}else {
							ruleStr.add(root.getName() + "-"  +c1.getName());
						}
					}
				} else {
					ruleStr.add(root.getName());
				}
				
			}
		}
		return ruleStr;
	}

	/**
	 * 列表页面
	 * @param userId 用户id
	 * @param type  类型 tree,其它
	 * @param status 状态
	 * @return
	 */
	public List<Map<String, Object>> getDataList(Integer userId,String type) {
		List<SysAdminRule> rulesList = getRulesByUserId(userId);
		if(type != null && "tree".equals(type)) {
			//处理树
			rulesList = this.buildByRecursiveTree(rulesList);
			List<Map<String, Object>> rawList = Lists.newArrayList();
			rulesList.forEach((m)->{
				Map<String, Object> map = BeanToMapUtil.convertBean(m);
				map.put("check", false);
				rawList.add(map);
			});
			return rawList;
		}else {
			Map<String, String> fields = Maps.newHashMap();
			fields.put("cid", "id");
			fields.put("fid", "pid");
			fields.put("name", "title");
			fields.put("fullname", "title");
			List<Map<String, Object>> rawList = Lists.newArrayList();
			rulesList.forEach((m)->{
				rawList.add(BeanToMapUtil.convertBean(m));
			});
			Category cate = new Category(fields, rawList);
			return cate.getList(Integer.valueOf("0"));
		}
		
	}
	
	
	/**
	 * 根据用户id查询所属的权限信息
	 * @param userId 用户id
	 * @return
	 */
	private List<SysAdminRule> getRulesByUserId(Integer userId) {
		List<SysAdminRule> rulesList = Lists.newArrayList();
		//判断是否为管理员
		if(userId.equals(1)) {
			rulesList = sysAdminRuleDao.selectByStatus(1);
		} else {
			//查询分组
			List<SysAdminGroup> groupsList = sysAdminGroupDao.selectByUserId(userId, (byte) 1);
			StringBuffer ruleIds = new StringBuffer(); 
			for(SysAdminGroup group : groupsList) {
				if(ruleIds.length() == 0) {
					ruleIds.append(group.getRules());
				} else {
					ruleIds.append(",").append(group.getRules());
				}
			}
			//查询权限
			rulesList = sysAdminRuleDao.selectInIds(ruleIds.toString(), 1);
		} 
		return rulesList;
	}
}

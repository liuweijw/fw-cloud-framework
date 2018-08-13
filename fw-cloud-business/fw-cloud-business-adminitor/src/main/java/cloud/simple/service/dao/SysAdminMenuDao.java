package cloud.simple.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cloud.simple.service.model.SysAdminMenu;
import cloud.simple.service.util.MyMapper;

public interface SysAdminMenuDao extends  MyMapper<SysAdminMenu>  {
	/**
	 * 根据ruleIds查询菜单信息
	 * @param ruleIds 权限id
	 * @param status 状态值
	 * @return List<SysAdminMenu>
	 */
	List<SysAdminMenu> selectInRuleIds(@Param("ruleIds") String ruleIds, @Param("status") int status);
}
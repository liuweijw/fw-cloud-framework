package cloud.simple.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cloud.simple.service.model.SysAdminRule;
import cloud.simple.service.util.MyMapper;

public interface SysAdminRuleDao extends  MyMapper<SysAdminRule>  {

	List<SysAdminRule> selectInIds(@Param("ruleIds") String ruleIds,@Param("status") Integer status);

	List<SysAdminRule> selectByStatus(@Param("status") Integer status);
}
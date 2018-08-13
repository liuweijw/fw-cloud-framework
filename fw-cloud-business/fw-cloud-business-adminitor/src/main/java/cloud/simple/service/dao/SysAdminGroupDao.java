package cloud.simple.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cloud.simple.service.model.SysAdminGroup;
import cloud.simple.service.util.MyMapper;

public interface SysAdminGroupDao extends  MyMapper<SysAdminGroup>  {
	/**
	 * 查询分组信息
	 * @param userId 用户ID
	 * @param status 状态
	 * @return
	 */
	List<SysAdminGroup> selectByUserId(@Param("userId") Integer userId,@Param("status") Byte status);
}
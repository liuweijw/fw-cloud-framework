package cloud.simple.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.simple.service.base.BaseServiceImpl;
import cloud.simple.service.dao.SysSystemConfigDao;
import cloud.simple.service.model.SysSystemConfig;
import tk.mybatis.mapper.common.Mapper;
@Service
public class SysSystemConfigService extends BaseServiceImpl<SysSystemConfig>{
	@Autowired
	private SysSystemConfigDao sysSystemConfigDao;
	
	@Override
	public Mapper<SysSystemConfig> getMapper() {
		return sysSystemConfigDao;
	}

}

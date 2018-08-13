package cloud.simple.service.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.simple.service.base.BaseServiceImpl;
import cloud.simple.service.model.SysAdminPost;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class SysAdminPostService extends BaseServiceImpl<SysAdminPost>{
	
	@Autowired
	private Mapper<SysAdminPost> sysAdminPostDao;
	
	@Override
	public Mapper<SysAdminPost> getMapper() {
		return sysAdminPostDao;
	}

	public List<SysAdminPost> getDataList(String name) {
		Example example = new Example(SysAdminPost.class,false);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(name)){
			criteria.andLike("name", name);
		}
		return sysAdminPostDao.selectByExample(example);
	}

}

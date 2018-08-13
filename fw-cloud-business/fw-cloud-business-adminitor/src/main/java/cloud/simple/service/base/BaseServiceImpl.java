package cloud.simple.service.base;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class BaseServiceImpl< T extends BaseEntity> implements BaseService<T>{
	
	//protected Mapper<T> mapper;
	
	public abstract Mapper<T> getMapper();
	
	@Override
	public T selectOne(T record) {
	
		List<T> results = getMapper().select(record);
		if(CollectionUtils.isNotEmpty(results)) return results.get(0);
		else return null;
	}
	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * @param record 影响行数
	 * @return list 列表
	 */
	@Override
	public List<T> select(T record) {
	
		return getMapper().select(record);
	}
	
	/**
	 * 根据实体类不为null的字段进行查询，条件全部使用=号and条件，并指定排序
	 * @param record 查询条件
	 * @param orderSqlStr 排序条件 （name ase,id desc）
	 * @return List 列表
	 */
	public List<T> select(T record,String orderSqlStr){
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		Map<String, String> map;
		try {
			map = BeanUtils.describe(record);
			for(Entry<String, String> entry : map.entrySet()){
				if(entry.getValue() == null || "".equals(entry.getValue())) continue;
					criteria.andEqualTo(entry.getKey(), entry.getValue());
			}
			example.setOrderByClause(orderSqlStr);
			return getMapper().selectByExample(example);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
	 * @param record 对象
	 * @return int  影响行数
	 */
	@Override
	public int selectCount(T record) {
	
		return getMapper().selectCount(record);
	}

	/**
	 * 根据主键进行查询,必须保证结果唯一 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param key 主键
	 * @return T  影响行数
	 */
	@Override
	public T selectByPrimaryKey(Object key) {
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 插入一条数据 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param record 对象
	 * @return int 影响行数
	 */
	@Override
	public int insert(T record) {
		return getMapper().insert(record);
	}

	/**
	 * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
	 * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param record 对象
	 * @return int 影响行数
	 */
	@Override
	public int insertSelective(T record) {
		return getMapper().insertSelective(record);
	}

	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * 
	 * @param key 主键
	 * @return int 影响行数
	 */
	@Override
	public int delete(T key) {
		return getMapper().delete(key);
	}

	/**
	 * 通过主键进行删除,这里最多只会删除一条数据 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param key 主键
	 * @return int 影响行数
	 */
	@Override
	public int deleteByPrimaryKey(Object key) {
		return getMapper().deleteByPrimaryKey(key);
	}

	/**
	 * 根据主键进行更新,这里最多只会更新一条数据 参数为实体类
	 * 
	 * @param record 对象
	 * @return int 影响行数
	 */
	@Override
	public int updateByPrimaryKey(T record) {
		return getMapper().updateByPrimaryKey(record);
	}

	/**
	 * 根据主键进行更新 只会更新不是null的数据
	 * 
	 * @param record 对象
	 * @return int 影响行数
	 */
	@Override
	public int updateByPrimaryKeySelective(T record) {
		return getMapper().updateByPrimaryKeySelective(record);
	}

	/**
	 * 保存或者更新，根据传入id主键是不是null来确认
	 * 
	 * @param record 对象
	 * @return int 影响行数
	 */
	@Override
	public int save(T record) {
		int count = 0;
		if (record.getId() == null) {
			count = this.insertSelective(record);
		} else {
			count = this.updateByPrimaryKeySelective(record);
		}
		return count;
	}

	/**
   	 *(单表分页可排序) 
   	 * @param pageNum 当前页
   	 * @param pageSize 页码
   	 * @param record 对象
   	 * @return PageInfo 分页对象
   	 */
	@Override
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<T>(getMapper().select(record));
	}
	
	/**
	 * (单表分页可排序) 
	 * @param pageNum 当前页
	 * @param pageSize 页码
	 * @param record 对象
	 * @param orderSqlStr (如:id desc)
	 * @return PageInfo 分页对象
	 */
	@Override
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record,String orderSqlStr) {
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		Map<String, Object> map;
		try {
			map = PropertyUtils.describe(record);
			for(Map.Entry<String, Object> entry : map.entrySet()){
				if(entry.getValue() == null || "".equals(entry.getValue())) continue;
				criteria.andEqualTo(entry.getKey(), entry.getValue());
			}
			example.setOrderByClause(orderSqlStr);
			PageHelper.startPage(pageNum, pageSize);
			List<T> list = getMapper().selectByExample(example);
			return new PageInfo<T>(list);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}

package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.Dept;
import com.github.liuweijw.business.admin.domain.QDept;
import com.github.liuweijw.business.admin.repository.DeptRepository;
import com.github.liuweijw.business.admin.service.DeptService;
import com.github.liuweijw.business.commons.tree.DeptTree;
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.commons.base.tree.TreeUtil;
import com.github.liuweijw.commons.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
@CacheConfig(cacheNames = AdminCacheKey.DEPT_INFO)
public class DeptServiceImpl extends JPAFactoryImpl implements DeptService {

	@Autowired
	private DeptRepository	deptRepository;

	@Override
	@Cacheable(key = "'page_dept_' + #p0.currentPage + '_' + #p0.pageSize + '_' + #p1.type + '_' + #p1.label")
	public PageBean<Dept> findAll(PageParams pageParams, Dept dept) {
		QDept qDept = QDept.dept;
		Predicate qNamePredicate = null;
		if (null != dept) {
			if (StringHelper.isNotBlank(dept.getDeptName())) {
				qNamePredicate = qDept.deptName.like("%" + dept.getDeptName().trim() + "%");
			}
		}
		Predicate predicate = qDept.deptId.goe(0).and(qNamePredicate);
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "deptId"));
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<Dept> pageList = deptRepository.findAll(predicate, pageRequest);

		return PageUtils.of(pageList);
	}

	@Override
	@Cacheable(key = "'dept_tree_list'", unless = "#result eq null")
	public List<DeptTree> getDeptTreeList() {
		QDept dept = QDept.dept;
		List<Dept> list = this.queryFactory.selectFrom(dept).where(dept.statu.eq(0)).fetch();
		if (null == list || list.size() == 0)
			return new ArrayList<DeptTree>();

		return getDeptTree(list, 0);
	}

	private List<DeptTree> getDeptTree(List<Dept> list, int rootId) {
		List<DeptTree> treeList = new ArrayList<DeptTree>();
		for (Dept dept : list) {
			// 排除父节点和自己节点相同的数据
			if (dept.getPid().intValue() == dept.getDeptId().intValue()) continue;
			DeptTree node = new DeptTree();
			node.setId(dept.getDeptId() + "");
			node.setPid(dept.getPid() + "");
			node.setName(dept.getDeptName());
			node.setPos(dept.getPos());
			treeList.add(node);
		}
		return TreeUtil.build(treeList, "0");
	}

	@Override
	@Cacheable(key = "'dept_info_' + #id", unless = "#result eq null")
	public Dept findById(Integer id) {
		if (null == id || id < 0) return null;

		return deptRepository.findOne(id);
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Dept saveOrUpdate(Dept dept) {
		if (null == dept) return null;

		return deptRepository.saveAndFlush(dept);
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean delById(Integer id) {
		if (null == id || id < 0) return false;

		QDept qDept = QDept.dept;
		Long num = this.queryFactory
				.update(qDept)
				.set(qDept.statu, 1)
				.where(qDept.deptId.eq(id))
				.execute();
		return null != num && num > 0;
	}

}

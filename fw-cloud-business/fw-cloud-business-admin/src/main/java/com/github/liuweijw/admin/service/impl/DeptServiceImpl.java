package com.github.liuweijw.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.admin.domain.Dept;
import com.github.liuweijw.admin.domain.QDept;
import com.github.liuweijw.admin.repository.DeptRepository;
import com.github.liuweijw.admin.service.DeptService;
import com.github.liuweijw.business.commons.tree.DeptTree;
import com.github.liuweijw.business.commons.util.TreeUtil;

@Component
public class DeptServiceImpl extends JPAFactoryImpl implements DeptService {

	@Autowired
	private DeptRepository deptRepository;

	@Override
	public List<DeptTree> getDeptTreeList() {
		QDept dept = QDept.dept;
		List<Dept> list = this.queryFactory.selectFrom(dept)
										   .where(dept.delFlag.eq(0))
										   .fetch();
		if(null == list || list.size() == 0) return new ArrayList<DeptTree>();
		
		return getDeptTree(list, 0);
	}

	private List<DeptTree> getDeptTree(List<Dept> list, int rootId) {
		List<DeptTree> treeList = new ArrayList<DeptTree>();
		for(Dept dept : list){
			// 排除父节点和自己节点相同的数据
			if(dept.getPid().intValue() == dept.getId().intValue()) continue;
			DeptTree node = new DeptTree();
			node.setId(dept.getId());
			node.setPid(dept.getPid());
			node.setName(dept.getName());
			treeList.add(node);
		}
		return TreeUtil.build(treeList, 0);
	}
}

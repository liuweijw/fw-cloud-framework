package com.github.liuweijw.business.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.QRole;
import com.github.liuweijw.business.admin.domain.QRoleDept;
import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.repository.RoleRepository;
import com.github.liuweijw.business.admin.service.RoleService;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.core.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
public class RoleServiceImpl extends JPAFactoryImpl implements RoleService {

	@Autowired
	private RoleRepository	roleRepository;

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_' + #roleCode")
	public Role findRoleByCode(String roleCode) {
		if (StringHelper.isBlank(roleCode)) return null;

		QRole qRole = QRole.role;
		return this.queryFactory.selectFrom(qRole).where(qRole.roleCode.eq(roleCode.trim()))
				.fetchOne();
	}

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_' + #deptId")
	public List<Role> getRoleListByDeptId(Integer deptId) {
		if (null == deptId || deptId <= 0) return null;

		// load role
		QRoleDept qRoleDept = QRoleDept.roleDept;
		QRole qRole = QRole.role;
		List<Role> rList = this.queryFactory.select(qRole).from(qRoleDept, qRole).where(
				qRoleDept.deptId.eq(deptId)).where(qRoleDept.roleId.eq(qRole.roleId)).fetch();

		return rList;
	}

	@Override
	@Cacheable(value = AdminCacheKey.ROLE_INFO, key = "'role_list'")
	public List<Role> getRoleList() {
		return roleRepository.findAll();
	}

	@Override
	public PageBean<Role> findAll(PageParams pageParams, Role role) {
		QRole qRole = QRole.role;
		// 用户名查询条件
		Predicate qNamePredicate = null;
		Predicate qCodePredicate = null;
		if (null != role) {
			if (StringHelper.isNotBlank(role.getRoleName())) {
				qNamePredicate = qRole.roleName.like("%" + role.getRoleName().trim() + "%");
			}
			if (StringHelper.isNotBlank(role.getRoleCode())) {
				qCodePredicate = qRole.roleCode.like("%" + role.getRoleCode().trim() + "%");
			}
		}

		Predicate predicate = qRole.roleId.goe(0).and(qCodePredicate).and(qNamePredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "roleId"));
		PageRequest pageRequest = new PageRequest(pageParams.getCurrentPage(), pageParams
				.getPageSize(), sort);
		Page<Role> pageList = roleRepository.findAll(predicate, pageRequest);

		PageBean<Role> pageData = new PageBean<Role>();
		pageData.setCurrentPage(pageParams.getCurrentPage());
		pageData.setPageSize(pageParams.getPageSize());
		pageData.setTotal(pageList.getTotalElements());
		pageData.setList(pageList.getContent());

		return pageData;
	}

	@Override
	@Transactional
	public Role saveOrUpdate(Role role) {
		if (null == role) return null;

		Role dbRole = this.roleRepository.saveAndFlush(role);
		this.redisCacheClear();

		return dbRole;
	}

	@Override
	@Transactional
	public boolean delById(Integer roleId) {
		if (null == roleId || roleId <= 0) return Boolean.FALSE;

		QRole qRole = QRole.role;
		long num = this.queryFactory.update(qRole).set(qRole.statu, 1) // 0 正常 1删除
				.where(qRole.roleId.eq(roleId.intValue())).execute();

		this.redisCacheClear();
		return num > 0;
	}

	@CacheEvict(value = { AdminCacheKey.ROLE_INFO }, allEntries = true)
	public void redisCacheClear() {
	}

}

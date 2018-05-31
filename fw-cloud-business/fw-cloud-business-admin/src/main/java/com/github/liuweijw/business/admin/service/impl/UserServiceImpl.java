package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.beans.UserBean;
import com.github.liuweijw.business.admin.beans.UserForm;
import com.github.liuweijw.business.admin.cache.AdminCacheKey;
import com.github.liuweijw.business.admin.domain.QRole;
import com.github.liuweijw.business.admin.domain.QUser;
import com.github.liuweijw.business.admin.domain.QUserRole;
import com.github.liuweijw.business.admin.domain.Role;
import com.github.liuweijw.business.admin.domain.User;
import com.github.liuweijw.business.admin.domain.UserRole;
import com.github.liuweijw.business.admin.repository.UserRepository;
import com.github.liuweijw.business.admin.repository.UserRoleRepository;
import com.github.liuweijw.business.admin.service.PermissionService;
import com.github.liuweijw.business.admin.service.UserService;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.core.beans.system.AuthRole;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
public class UserServiceImpl extends JPAFactoryImpl implements UserService {

	@Autowired
	private UserRepository		userRepository;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate		redisTemplate;

	@Autowired
	private PermissionService	permissionService;

	@Autowired
	private UserRoleRepository	userRoleRepository;

	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO, key = "'user_' + #username")
	public AuthUser findUserByUsername(String username) {
		User user = findUserByUsername(username, true);

		return buildAuthUserByUser(user);
	}

	@Override
	public User findUserByUsername(String username, boolean isLoadRole) {

		if (StringHelper.isBlank(username)) return null;

		User user = userRepository.findUserByUsername(username.trim());
		if (null == user) return null;

		if (isLoadRole) user.setRoleList(findRoleListByUserId(user.getUserId()));

		return user;
	}

	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO, key = "'user_' + #mobile")
	public AuthUser findUserByMobile(String mobile) {
		User user = userRepository.findUserByMobile(mobile.trim());
		if (null == user) return null;

		user.setRoleList(findRoleListByUserId(user.getUserId()));

		return buildAuthUserByUser(user);
	}

	public List<Role> findRoleListByUserId(Integer userId) {
		if (null == userId) return null;

		// load role
		QUserRole qUserRole = QUserRole.userRole;
		QRole qRole = QRole.role;
		List<Role> rList = this.queryFactory.select(qRole).from(qUserRole, qRole).where(
				qUserRole.userId.eq(userId)).where(qUserRole.roleId.eq(qRole.roleId)).fetch();

		return rList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveImageCode(String randomStr, String imageCode) {
		redisTemplate.opsForValue().set(SecurityConstant.DEFAULT_CODE_KEY + randomStr, imageCode,
				SecurityConstant.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
	}

	@Override
	public UserBean findUserInfo(AuthUser user) {
		User dbUser = findUserByUsername(user.getUsername(), false);

		UserBean userInfo = new UserBean();
		// 过滤关键信息
		dbUser.setPassword("");
		dbUser.setCreateTime(null);
		dbUser.setUpdateTime(null);
		userInfo.setUser(dbUser);

		// 设置角色列表
		List<AuthRole> roleList = user.getRoleList();
		List<String> roleCodes = new ArrayList<>();

		roleList.stream().forEach(r -> {
			roleCodes.add(r.getRoleCode());
		});

		String[] roles = roleCodes.toArray(new String[roleCodes.size()]);

		userInfo.setRoles(roles);

		// 设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<String>();
		for (String roleCode : roles) {
			permissions.addAll(permissionService.findMenuPermissions(roleCode));
		}

		userInfo.setPermissions(permissions.toArray(new String[permissions.size()]));

		return userInfo;
	}

	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO, key = "'user_' + #userId")
	public AuthUser findByUserId(String userId) {
		User user = userRepository.findUserByUserId(Integer.valueOf(userId));
		if (null == user) return null;

		user.setRoleList(findRoleListByUserId(user.getUserId()));

		return buildAuthUserByUser(user);
	}

	private AuthUser buildAuthUserByUser(User user) {
		if (null == user) return null;

		AuthUser authUser = new AuthUser();
		authUser.setPicUrl(user.getPicUrl());
		authUser.setStatu(user.getStatu());
		authUser.setPassword(user.getPassword());
		authUser.setUserId(user.getUserId());
		authUser.setUsername(user.getUsername());

		if (null == user.getRoleList() || user.getRoleList().size() == 0) return authUser;
		List<AuthRole> rList = new ArrayList<AuthRole>();
		for (Role r : user.getRoleList()) {
			AuthRole aRole = new AuthRole();
			aRole.setStatu(r.getStatu());
			aRole.setRoleCode(r.getRoleCode());
			aRole.setRoleDesc(r.getRoleDesc());
			aRole.setRoleId(r.getRoleId());
			aRole.setRoleName(r.getRoleName());
			rList.add(aRole);
		}
		authUser.setRoleList(rList);

		return authUser;
	}

	@Override
	public PageBean<User> findAll(PageParams pageParams, User user) {
		QUser qUser = QUser.user;
		// 用户名查询条件
		Predicate qUserNamePredicate = null;
		if (null != user && StringHelper.isNotBlank(user.getUsername())) {
			qUserNamePredicate = qUser.username.like("%" + user.getUsername().trim() + "%");
		}

		Predicate predicate = qUser.statu.eq(0).and(qUserNamePredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<User> pageList = userRepository.findAll(predicate, pageRequest);
		if (null != pageList && null != pageList.getContent()) {
			for (User dbUser : pageList.getContent()) {
				dbUser.setRoleList(findRoleListByUserId(dbUser.getUserId()));
			}
		}
		return PageUtils.of(pageList);
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.USER_INFO }, allEntries = true)
	@Transactional
	public Boolean delByUserId(Integer userId) {
		if (null == userId || userId <= 0) return Boolean.FALSE;

		QUser qUser = QUser.user;
		long num = this.queryFactory.update(qUser).set(qUser.statu, 1) // 0 正常 1删除
				.where(qUser.userId.eq(userId.intValue())).execute();

		return num > 0;
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.USER_INFO }, allEntries = true)
	@Transactional
	public boolean addUserAndRole(User user, Integer roleId) {

		User dbUser = this.userRepository.saveAndFlush(user);

		UserRole uRole = new UserRole();
		uRole.setRoleId(roleId);
		uRole.setUserId(dbUser.getUserId());

		this.userRoleRepository.saveAndFlush(uRole);
		return true;
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.USER_INFO }, allEntries = true)
	@Transactional
	public boolean updateUserAndRole(UserForm userForm) {
		if (null == userForm.getUserId() || userForm.getUserId() <= 0) return Boolean.FALSE;

		User user = userRepository.findUserByUserId(userForm.getUserId());
		if (null == user) return false;

		user.setStatu(userForm.getStatu());
		user.setUpdateTime(new Date());
		user.setUserId(userForm.getUserId());
		user.setUsername(userForm.getUsername());
		userRepository.save(user);

		QUserRole qUserRole = QUserRole.userRole;

		this.queryFactory.delete(qUserRole).where(qUserRole.userId.eq(userForm.getUserId()))
				.execute();

		UserRole uRole = new UserRole();
		uRole.setRoleId(userForm.getRoleId());
		uRole.setUserId(userForm.getUserId());

		this.userRoleRepository.saveAndFlush(uRole);

		return true;
	}

	@Override
	@CacheEvict(value = { AdminCacheKey.USER_INFO }, allEntries = true)
	@Transactional
	public boolean updateUser(User user) {
		if (null == user || null == user.getUserId()) return false;

		this.userRepository.saveAndFlush(user);

		return true;
	}
}

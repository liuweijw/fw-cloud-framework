package com.github.liuweijw.business.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
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
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.commons.utils.WebUtils;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.system.api.model.AuthRole;
import com.github.liuweijw.system.api.model.AuthUser;

@Component
@CacheConfig(cacheNames = AdminCacheKey.USER_INFO)
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
	@Cacheable(key = "'user_name_' + #username")
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
	@Cacheable(key = "'user_mobile_' + #mobile")
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
	@Cacheable(key = "'user_id_' + #userId")
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
	@Cacheable(key = "'page_user_' + #p0.currentPage + '_' + #p0.pageSize + '_' + #p1.username")
	public PageBean<User> findAll(PageParams pageParams, User user) {
		// 复杂SQL举例查询
		// 总记录数
		StringBuilder countSql = new StringBuilder();
		countSql.append("select count(t1.user_id) from " + User.TABLE_NAME + " t1 ");

		// 查询语句
		StringBuilder querySql = new StringBuilder();
		querySql.append("select ")
				.append("t1.user_id, t1.username, t1.open_id, t1.mobile,t1.pic_url,t1.dept_id, t1.create_time, t1.update_time, t1.statu,")
				.append("t3.dept_name as deptName ")
				.append("from " + User.TABLE_NAME + " t1 ")
				.append("left join t_sys_dept t3 on t3.dept_id = t1.dept_id ");

		// where语句
		StringBuilder whereSql = new StringBuilder("where t1.statu=0 ");
		if (StringHelper.isNotBlank(user.getUsername())) {
			whereSql.append("and t1.username like ")
					.append("'%" + user.getUsername().trim() + "%' escape '!' ");
		}

		// 结果集列表
		List<User> rdList = new ArrayList<User>();

		Object countResult = this.em.createNativeQuery(countSql.append(whereSql).toString()).getSingleResult();
		Long resultCount = WebUtils.parseStrToLong(countResult + "", 0l);
		if (null != resultCount && resultCount > 0) {
			// order 语句
			StringBuilder orderSql = new StringBuilder("order by t1.create_time desc ");

			Query query = this.em.createNativeQuery(querySql.append(whereSql).append(orderSql).toString())
					.setFirstResult((pageParams.getCurrentPage() - 1) * pageParams.getPageSize())
					.setMaxResults(pageParams.getPageSize());

			// 下面转换为map （效率相对差一点）, 否则为 object[]
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> dList = query.getResultList();
			if (null != dList) {
				dList.forEach(d -> {
					User userBean = JSONObject.parseObject(JSONObject.toJSONString(d), User.class);
					rdList.add(userBean);
					userBean.setRoleList(findRoleListByUserId(userBean.getUserId()));
				});
			}

		}

		return PageUtils.of(rdList, resultCount, pageParams.getCurrentPage(), pageParams.getPageSize());
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public Boolean delByUserId(Integer userId) {
		if (null == userId || userId <= 0) return Boolean.FALSE;

		QUser qUser = QUser.user;
		long num = this.queryFactory.update(qUser).set(qUser.statu, 1) // 0 正常 1删除
				.where(qUser.userId.eq(userId.intValue()))
				.execute();

		return num > 0;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean addUserAndRoleDept(UserForm userForm) {

		User user = new User();
		user.setCreateTime(new Date());
		user.setStatu(0);
		user.setDeptId(userForm.getDeptId());
		user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword().trim()));
		user.setUpdateTime(new Date());
		user.setUsername(userForm.getUsername().trim());
		user.setMobile(userForm.getMobile());

		User dbUser = this.userRepository.saveAndFlush(user);

		UserRole uRole = new UserRole();
		uRole.setRoleId(userForm.getRoleId());
		uRole.setUserId(dbUser.getUserId());

		this.userRoleRepository.saveAndFlush(uRole);
		return true;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean updateUserAndRoleDept(UserForm userForm) {
		if (null == userForm.getUserId() || userForm.getUserId() <= 0) return Boolean.FALSE;

		User user = userRepository.findUserByUserId(userForm.getUserId());
		if (null == user) return false;

		user.setStatu(userForm.getStatu());
		user.setUpdateTime(new Date());
		user.setUserId(userForm.getUserId());
		user.setDeptId(userForm.getDeptId());
		user.setUsername(userForm.getUsername());
		user.setMobile(userForm.getMobile());
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
	@Transactional
	@CacheEvict(allEntries = true)
	public boolean updateUser(User user) {
		if (null == user || null == user.getUserId()) return false;

		this.userRepository.saveAndFlush(user);

		return true;
	}
}

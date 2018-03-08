package com.github.liuweijw.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.beans.UserForm;
import com.github.liuweijw.admin.domain.QRole;
import com.github.liuweijw.admin.domain.QUser;
import com.github.liuweijw.admin.domain.QUserRole;
import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.domain.User;
import com.github.liuweijw.admin.domain.UserRole;
import com.github.liuweijw.admin.repository.UserRepository;
import com.github.liuweijw.admin.repository.UserRoleRepository;
import com.github.liuweijw.admin.service.AdminCacheKey;
import com.github.liuweijw.admin.service.MenuService;
import com.github.liuweijw.admin.service.UserService;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;
import com.github.liuweijw.core.beans.system.AuthRole;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
public class UserServiceImpl extends JPAFactoryImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
    private MenuService menuService;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO, key = AdminCacheKey.USER_INFO_KEY_USERNAME)
	public AuthUser findUserByUsername(String username) {
		User user = findUserByUsername(username,true);
		
		return buildAuthUserByUser(user);
	}

	@Override
	public User findUserByUsername(String username, boolean isLoadRole) {
		
		if(StringHelper.isBlank(username)) return null;
		
		User user = userRepository.findUserByUsername(username.trim());
		if(null == user) return null;
		
		if(isLoadRole) user.setRoleList(findRoleListByUserId(user.getUserId()));
		
		return user;
	}
	
	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO_MOBILE, key = AdminCacheKey.USER_INFO_MOBILE_KEY_USERNAME)
	public AuthUser findUserByMobile(String mobile) {
		User user = userRepository.findUserByMobile(mobile.trim());
		if(null == user) return null;
		
		user.setRoleList(findRoleListByUserId(user.getUserId()));
		
		return buildAuthUserByUser(user);
	}

	public List<Role> findRoleListByUserId(Integer userId) {
		if(null == userId) return null;
		
		// load role
		QUserRole qUserRole = QUserRole.userRole;
		QRole qRole = QRole.role;
		List<Role> rList = this.queryFactory.select(qRole)
											.from(qUserRole,qRole)
											.where(qUserRole.userId.eq(userId))
											.where(qUserRole.roleId.eq(qRole.roleId))
											.fetch();
		
		return rList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveImageCode(String randomStr, String imageCode) {
		redisTemplate.opsForValue().set(SecurityConstant.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstant.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);		
	}

	@Override
	public UserBean findUserInfo(AuthUser user) {
		User dbUser = findUserByUsername(user.getUsername(),false);

        UserBean userInfo = new UserBean();
        // 过滤关键信息
        dbUser.setPassword("");
        dbUser.setCreateTime(null);
        dbUser.setUpdateTime(null);
        userInfo.setUser(dbUser);
        
        //设置角色列表
        List<AuthRole> roleList = user.getRoleList();
        List<String> roleNames = new ArrayList<>();
        if(null == roleList || roleList.size() == 0){
        	List<Role> dbRoleList = findRoleListByUserId(dbUser.getUserId());
        	dbRoleList.stream().forEach(r -> {
        		roleNames.add(r.getRoleName());
        	});
        } else {
        	roleList.stream().forEach(r -> {
        		roleNames.add(r.getRoleName());
        	});
        }
        
        String[] roles = roleNames.toArray(new String[roleNames.size()]);
        userInfo.setRoles(roles);
        
        //设置权限列表（menu.permission）
        String[] permissions = menuService.findPermission(roles);
        userInfo.setPermissions(permissions);
        
        return userInfo;
	}

	@Override
	// 待排查是什么问题导致转换失败
	//@Cacheable(value = AdminCacheKey.USER_INFO_USERID, key = AdminCacheKey.USER_INFO_USERID_KEY_USERID)
	public AuthUser findByUserId(String userId) {
		User user = userRepository.findUserByUserId(Integer.valueOf(userId));
		if(null == user) return null;
		
		user.setRoleList(findRoleListByUserId(user.getUserId()));
		
		return buildAuthUserByUser(user);
	}
	
	private AuthUser buildAuthUserByUser(User user){
    	if(null == user) return null;
    	
    	AuthUser authUser = new AuthUser();
    	authUser.setAvatar(user.getAvatar());
    	authUser.setDelFlag(user.getDelFlag());
    	authUser.setPassword(user.getPassword());
    	authUser.setUserId(user.getUserId());
    	authUser.setUsername(user.getUsername());
    	authUser.setDeptId(user.getDeptId());
    	
    	if(null == user.getRoleList() || user.getRoleList().size() == 0) return authUser;
    	List<AuthRole> rList = new ArrayList<AuthRole>();
    	for(Role r : user.getRoleList()){
    		AuthRole aRole = new AuthRole();
    		aRole.setDelFlag(r.getDelFlag());
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
		if(null != user && StringHelper.isNotBlank(user.getUsername())){
			qUserNamePredicate = qUser.username.like("%"+user.getUsername().trim()+"%");
		}
		
		Predicate predicate = qUser.delFlag.eq(0).and(qUserNamePredicate);
		
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"));
        PageRequest pageRequest = new PageRequest(pageParams.getPageNo(),pageParams.getPageNum(),sort);
        Page<User> pageList = userRepository.findAll(predicate,pageRequest);
        
        if(null != pageList && null != pageList.getContent()){
        	for(User dbUser : pageList.getContent()){
        		dbUser.setRoleList(findRoleListByUserId(dbUser.getUserId()));
        	}
        }
        
        PageBean<User> pageData = new PageBean<User>();
        pageData.setPageNo(pageParams.getPageNo());
        pageData.setPageNum(pageParams.getPageNum());
        pageData.setTotal(pageList.getTotalElements());
        pageData.setList(pageList.getContent());
        
		return pageData;
	}

	@Override
	@Transactional
	public Boolean delByUserId(Integer userId) {
		if(null == userId || userId <= 0) return Boolean.FALSE;
		
		QUser qUser = QUser.user;
		long num = this.queryFactory.update(qUser)
									.set(qUser.delFlag, 1) // 0 正常 1删除
									.where(qUser.userId.eq(userId.intValue()))
									.execute();
		return num > 0;
	}

	@Override
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
	@Transactional
	public boolean updateUserAndRole(UserForm userForm) {
		if(null == userForm.getUserId() || userForm.getUserId() <= 0) return Boolean.FALSE;
		
		User user = userRepository.findUserByUserId(userForm.getUserId());
		if(null == user) return false;
		
		user.setDelFlag(userForm.getDelFlag());
		user.setDeptId(userForm.getDeptId());
		user.setUpdateTime(new Date());
		user.setUserId(userForm.getUserId());
		user.setUsername(userForm.getUsername());
		userRepository.save(user);
		
		QUserRole qUserRole = QUserRole.userRole;
		
		this.queryFactory.delete(qUserRole)
						 .where(qUserRole.userId.eq(userForm.getUserId()))
						 .execute();
		
		UserRole uRole = new UserRole();
		uRole.setRoleId(userForm.getRoleId());
		uRole.setUserId(userForm.getUserId());
		
		this.userRoleRepository.saveAndFlush(uRole);
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {
		if(null == user || null == user.getUserId()) return false;
		
		this.userRepository.saveAndFlush(user);
		
		return true;
	}

}

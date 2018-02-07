package com.github.liuweijw.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.github.liuweijw.admin.beans.UserBean;
import com.github.liuweijw.admin.domain.QRole;
import com.github.liuweijw.admin.domain.QUserRole;
import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.domain.User;
import com.github.liuweijw.admin.repository.UserRepository;
import com.github.liuweijw.admin.service.AdminCacheKey;
import com.github.liuweijw.admin.service.MenuService;
import com.github.liuweijw.admin.service.UserService;
import com.github.liuweijw.core.beans.system.AuthRole;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.SecurityConstants;
import com.github.liuweijw.core.utils.StringHelper;

@Component
public class UserServiceImpl extends JPAFactoryImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
    private MenuService menuService;
	
	@Override
	@Cacheable(value = AdminCacheKey.USER_INFO, key = AdminCacheKey.USER_INFO_KEY_USERNAME)
	public AuthUser findUserByUsername(String username) {
		User user = findUserByUsername(username,true);
		
		return buildAuthUserByUser(user);
	}

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
											.where(qUserRole.user_id.eq(userId))
											.where(qUserRole.role_id.eq(qRole.roleId))
											.fetch();
		
		return rList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveImageCode(String randomStr, String imageCode) {
		redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);		
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
	@Cacheable(value = AdminCacheKey.USER_INFO_USERID, key = AdminCacheKey.USER_INFO_USERID_KEY_USERID)
	public AuthUser selectById(Integer userId) {
		User user = userRepository.findUserByUserId(userId);
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

}

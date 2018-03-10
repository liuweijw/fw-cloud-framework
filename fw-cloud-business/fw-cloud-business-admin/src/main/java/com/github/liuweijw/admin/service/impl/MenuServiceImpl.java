package com.github.liuweijw.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.admin.cache.AdminCacheKey;
import com.github.liuweijw.admin.domain.Menu;
import com.github.liuweijw.admin.domain.QMenu;
import com.github.liuweijw.admin.domain.QRoleMenu;
import com.github.liuweijw.admin.domain.QRoleMenuPermission;
import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.domain.RoleMenuPermission;
import com.github.liuweijw.admin.repository.MenuRepository;
import com.github.liuweijw.admin.repository.RoleRepository;
import com.github.liuweijw.admin.service.MenuService;
import com.github.liuweijw.core.beans.system.AuthMenu;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.utils.Assert;
import com.github.liuweijw.core.utils.StringHelper;

@Component
public class MenuServiceImpl extends JPAFactoryImpl implements MenuService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	@Cacheable(value = AdminCacheKey.MENU_INFO, key = AdminCacheKey.MENU_INFO_KEY_ROLECODE)
	public Set<AuthMenu> findMenuByRole(String roleCode) {
		if(StringHelper.isBlank(roleCode)) return null;

		Role role = roleRepository.findRoleByRoleCode(roleCode.trim());
		if(null == role) return null;

		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
		QMenu qMenu = QMenu.menu;
		List<Menu> rList = this.queryFactory.select(qMenu)
											.from(qRoleMenu,qMenu)
											.where(qRoleMenu.roleId.eq(role.getRoleId()))
											.where(qRoleMenu.menuId.eq(qMenu.menuId))
											.fetch();
		
		if(null == rList || rList.size() == 0) return null;
		
		Set<AuthMenu> mList = new HashSet<AuthMenu>();
    	for(Menu m : rList){
    		AuthMenu authMenu = new AuthMenu();
    		BeanUtils.copyProperties(m, authMenu);
    		mList.add(authMenu);
    	}
		return mList;
	}

	@Override
	public String[] findPermission(String[] roleCodes) {
		Set<String> permissions = new HashSet<>();
        for (String roleCode : roleCodes) {
        	// 查询Role
        	Role role = roleRepository.findRoleByRoleCode(roleCode.trim());
    		if(null == role) continue;
    		// 查询菜单
    		QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
    		QRoleMenuPermission qRoleMenuPermission = QRoleMenuPermission.roleMenuPermission;
    		List<RoleMenuPermission> rList = this.queryFactory.select(qRoleMenuPermission)
    											.from(qRoleMenuPermission,qRoleMenu)
    											.where(qRoleMenu.roleId.eq(role.getRoleId()))
    											.where(qRoleMenuPermission.roleMenuId.eq(qRoleMenu.id))
    											.fetch();
    		
            if(null == rList || rList.size() == 0) continue;
            
            rList.stream().forEach(r -> {
            	permissions.add(r.getPermission());
            });
        }

        return permissions.toArray(new String[permissions.size()]);
	}

	@Override
	@CacheEvict(value = AdminCacheKey.MENU_INFO, key = AdminCacheKey.MENU_INFO_KEY_ROLECODE)
	@Transactional
	public Boolean deleteMenu(Integer menuId, String roleCode) {
		Assert.isNull(menuId, "菜单ID不能为空");
		
		// 删除当前节点 -- 假删除
		QMenu qMenu = QMenu.menu;
		this.queryFactory.update(qMenu)
						 .set(qMenu.statu, CommonConstant.STATUS_DEL)
						 .where(qMenu.menuId.eq(menuId)).execute();
		
        // 删除父节点为当前节点的节点 -- 假删除
		this.queryFactory.update(qMenu)
		 				 .set(qMenu.statu, CommonConstant.STATUS_DEL)
		 				 .where(qMenu.pid.eq(menuId)).execute();
		
        return true;
	}

	@Override
	@CacheEvict(value = AdminCacheKey.MENU_INFO, key = AdminCacheKey.MENU_INFO_KEY_ROLECODE)
	public Boolean updateMenuById(Menu menu,String roleCode) {
		
		if(null == menu || null == menu.getMenuId()) return null;
		
		menuRepository.saveAndFlush(menu);
		
		return true;
	}

}

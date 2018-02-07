package com.github.liuweijw.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.admin.domain.Menu;
import com.github.liuweijw.admin.domain.QMenu;
import com.github.liuweijw.admin.domain.QRoleMenu;
import com.github.liuweijw.admin.domain.Role;
import com.github.liuweijw.admin.repository.MenuRepository;
import com.github.liuweijw.admin.repository.RoleRepository;
import com.github.liuweijw.admin.service.AdminCacheKey;
import com.github.liuweijw.admin.service.MenuService;
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
	public Set<Menu> findMenuByRole(String roleCode) {
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
		
		Set<Menu> mList = new HashSet<Menu>();
		for(Menu m : rList){
			mList.add(m);
		}
		
		return mList;
	}

	@Override
	public String[] findPermission(String[] roleCodes) {
		Set<Menu> menuSet = new HashSet<>();
        for (String roleCode : roleCodes) {
            Set<Menu> menu = findMenuByRole(roleCode);
            if(null != menu) menuSet.addAll(menu);
        }

        Set<String> permissions = new HashSet<>();
        for (Menu menu : menuSet) {
            if (StringUtils.isNotEmpty(menu.getPermission())) {
                String permission = menu.getPermission();
                permissions.add(permission);
            }
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
						 .set(qMenu.delFlag, CommonConstant.STATUS_DEL)
						 .where(qMenu.menuId.eq(menuId)).execute();
		
        // 删除父节点为当前节点的节点 -- 假删除
		this.queryFactory.update(qMenu)
		 				 .set(qMenu.delFlag, CommonConstant.STATUS_DEL)
		 				 .where(qMenu.parentId.eq(menuId)).execute();
		
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

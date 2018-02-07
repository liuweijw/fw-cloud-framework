package com.github.liuweijw.admin.service;

import java.util.Set;

import com.github.liuweijw.admin.domain.Menu;

public interface MenuService {

	/**
     * 通过角色名称查询URL 权限
     *
     * @param roleCode 角色名称
     * @return 菜单列表
     */
    Set<Menu> findMenuByRole(String roleCode);

    /**
     * 通过角色获取菜单权限列表
     *
     * @param roleCodes 角色
     * @return 权限列表
     */
    String[] findPermission(String[] roleCodes);

    /**
     * 级联删除菜单
     *
     * @param id   菜单ID
     * @param roleCode 角色
     * @return 成功、失败
     */
    Boolean deleteMenu(Integer id, String roleCode);

    /**
     * 更新菜单信息
     *
     * @param sysMenu 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(Menu sysMenu,String roleCode);
    
}

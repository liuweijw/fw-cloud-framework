package com.github.liuweijw.business.admin.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.github.liuweijw.business.commons.tree.MenuTree;

/**
 * 用于redis缓存的必须实现Serializable 接口
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class MenuTreeBean implements Serializable {

	private static final long	serialVersionUID	= 2707121320504244801L;

	private List<MenuTree>		menuList;

	private String[]			permissions;

}

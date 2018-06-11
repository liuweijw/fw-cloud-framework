package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = RoleMenu.TABLE_NAME)
public class RoleMenu implements Serializable {

	private static final long	serialVersionUID	= 8409879328945905867L;

	public static final String	TABLE_NAME			= "t_sys_role_menu";

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	/**
	 * 角色Id
	 */
	@Column(name = "role_id")
	private Integer				roleId;

	/**
	 * 菜单id
	 */
	@Column(name = "menu_id")
	private Integer				menuId;

}

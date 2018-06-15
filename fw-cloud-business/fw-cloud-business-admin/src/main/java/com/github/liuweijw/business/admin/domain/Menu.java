package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = Menu.TABLE_NAME)
public class Menu implements Serializable {

	private static final long	serialVersionUID	= -7932712605092646920L;

	public static final String	TABLE_NAME			= "t_sys_menu";

	/**
	 * 菜单ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "menu_id")
	private Integer				menuId;

	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name")
	private String				menuName;

	/**
	 * 请求路径
	 */
	@Column(name = "path")
	private String				path;

	/**
	 * 请求链接
	 */
	@Column(name = "url")
	private String				url;

	/**
	 * 父菜单ID
	 */
	@Column(name = "pid")
	private Integer				pid;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date				createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date				updateTime;

	/**
	 * 0-正常，1-删除
	 */
	@Column(name = "statu")
	private Integer				statu				= 0;

}

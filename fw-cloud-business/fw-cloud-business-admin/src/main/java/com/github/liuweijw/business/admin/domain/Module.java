package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.liuweijw.business.commons.permission.Functional;

/**
 * 方法上标注需要的权限 {@link Functional }
 * 
 * @author liuweijw
 */
@Entity
@Table(name = Module.TABLE_NAME)
public class Module implements Serializable {

	private static final long	serialVersionUID	= 2657654159968503284L;

	public static final String	TABLE_NAME			= "t_sys_module";

	/**
	 * 部门id
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	/**
	 * 编码 {@link Functional }
	 */
	@Column(name = "code")
	private String				code;

	/**
	 * 名称
	 */
	@Column(name = "name")
	private String				name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

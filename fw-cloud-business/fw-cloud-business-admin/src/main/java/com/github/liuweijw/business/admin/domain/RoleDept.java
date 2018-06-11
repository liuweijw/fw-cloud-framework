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
@Table(name = RoleDept.TABLE_NAME)
public class RoleDept implements Serializable {

	private static final long	serialVersionUID	= -1028920072723837099L;

	public static final String	TABLE_NAME			= "t_sys_role_dept";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	@Column(name = "role_id")
	private Integer				roleId;

	@Column(name = "dept_id")
	private Integer				deptId;

}

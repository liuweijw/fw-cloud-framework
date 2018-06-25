package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门关联信息
 * 
 * @author liuweijw
 */
@Setter
@Getter
@Entity
@Table(name = DeptRelation.TABLE_NAME)
public class DeptRelation implements Serializable {

	private static final long	serialVersionUID	= -6556339933726822514L;

	public static final String	TABLE_NAME			= "t_sys_dept_relation";

	@Id
	@Column(name = "pre_id")
	private Integer				preId;

	@Column(name = "after_id")
	private Integer				afterId;

}

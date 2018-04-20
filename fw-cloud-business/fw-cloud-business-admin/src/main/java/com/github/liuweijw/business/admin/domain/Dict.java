package com.github.liuweijw.business.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门信息
 * 
 * @author liuweijw
 */
@Entity
@Table(name = Dict.TABLE_NAME)
public class Dict implements Serializable {

	private static final long	serialVersionUID	= 6336455104080722637L;

	public static final String	TABLE_NAME			= "t_sys_dict";

	/**
	 * 部门id
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer				id;

	/**
	 * 类型
	 */
	@Column(name = "type")
	private String				type;

	/**
	 * 数据值
	 */
	@Column(name = "value")
	private String				value;

	/**
	 * 标签名
	 */
	@Column(name = "label")
	private String				label;

	/**
	 * 0--正常 1--删除
	 */
	@Column(name = "statu")
	private Integer				statu				= 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

}

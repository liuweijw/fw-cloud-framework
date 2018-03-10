package com.github.liuweijw.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门信息
 * 
 * @author liuweijw
 *
 */
@Entity
@Table(name = Dept.TABLE_NAME)
public class Dept implements Serializable {

	private static final long serialVersionUID = -3406853201428676677L;

	public static final String TABLE_NAME = "t_sys_dept";
	
	/**
     * 部门id
     */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	/**
     * 上级部门
     */
	@Column(name = "pid")
	private Integer pid;
	
	/**
     * 部门名称
     */
	@Column(name = "dept_name")
	private String deptName;
	
	/**
     * 0--正常 1--删除
     */
	@Column(name = "statu")
    private Integer statu = 0;
	
	/**
     * 排序字段
     */
	@Column(name = "pos")
	private Integer pos;
	
	/**
     * 创建时间
     */
	@Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
	@Column(name = "update_time")
    private Date updateTime;
	
	public Dept(){
		
	}
	
	public Dept(Integer id, Integer pid, String deptName){
		this.id = id;
		this.pid = pid;
		this.deptName = deptName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

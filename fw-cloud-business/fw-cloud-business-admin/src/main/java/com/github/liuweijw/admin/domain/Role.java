package com.github.liuweijw.admin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuweijw
 */
@Entity
@Table(name = Role.TABLE_NAME)
public class Role implements Serializable {

	private static final long serialVersionUID = -5794622871292709802L;

	public static final String TABLE_NAME = "t_sys_role";
	
	/**
	 * 角色ID
	 */
    @Id
	@GeneratedValue
	@Column(name = "role_id")
    private Integer roleId;
    
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;
    
    /**
     * 角色编码，唯一
     */
    @Column(name = "role_code")
    private String roleCode;
    
    /**
     * 描述
     */
    @Column(name = "role_desc")
    private String roleDesc;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    
    /**
     * 0-正常，1-删除
     */
    @Column(name = "statu")
    private Integer statu = 0;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

}

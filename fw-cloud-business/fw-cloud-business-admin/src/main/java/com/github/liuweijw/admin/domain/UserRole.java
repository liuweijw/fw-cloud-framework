package com.github.liuweijw.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuweijw
 */
@Entity
@Table(name = UserRole.TABLE_NAME)
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8409879328945905867L;

	public static final String TABLE_NAME = "t_sys_user_role";

	/**
     * 主键ID
     */
    @Id
	@GeneratedValue
	@Column(name = "sid")
    private Integer sid;
    
	@Column(name="user_id")
	private Integer user_id;
	
	@Column(name="role_id")
	private Integer role_id;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

}

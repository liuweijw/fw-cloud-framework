package com.github.liuweijw.system.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.system.api.model.AuthRole;
import com.github.liuweijw.system.api.model.AuthUser;

public class UserDetailsImpl implements UserDetails {

	private static final long	serialVersionUID	= 4996108569522332042L;

	private String				username;

	private String				password;

	private Integer				status				= 0;

	private List<AuthRole>		roleList			= new ArrayList<>();

	public UserDetailsImpl(AuthUser authUser) {
		this.username = authUser.getUsername();
		this.password = authUser.getPassword();
		this.status = authUser.getStatu();
		this.roleList = authUser.getRoleList();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		for (AuthRole role : roleList) {
			authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()));
		}
		return authorityList;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return CommonConstant.STATUS_LOCK.intValue() == status.intValue() ? false : true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return CommonConstant.STATUS_NORMAL.intValue() == status.intValue() ? true : false;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<AuthRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AuthRole> roleList) {
		this.roleList = roleList;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

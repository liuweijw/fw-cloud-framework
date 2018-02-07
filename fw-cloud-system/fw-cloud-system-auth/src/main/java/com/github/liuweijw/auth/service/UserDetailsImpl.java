package com.github.liuweijw.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.liuweijw.core.beans.system.AuthRole;
import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.CommonConstant;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 4996108569522332042L;
	
	private String username;
    private String password;
    private String status;
    private List<AuthRole> roleList = new ArrayList<>();

    public UserDetailsImpl(AuthUser authUser) {
        this.username = authUser.getUsername();
        this.password = authUser.getPassword();
        this.status = authUser.getDelFlag();
        this.roleList = authUser.getRoleList();
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<>();
        for (AuthRole role : roleList) {
        	System.out.println("=====auth==getAuthorities()==========" + role.getRoleCode());
            authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        //authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
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
        return StringUtils.equals(CommonConstant.STATUS_LOCK, status) ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(CommonConstant.STATUS_NORMAL, status) ? true : false;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

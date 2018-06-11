package com.github.liuweijw.system.api.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户菜单权限")
public class AuthPermission implements Serializable {

	private static final long	serialVersionUID	= 4566420419542436770L;

	/**
	 * 请求URL
	 */
	private String				url;

}

package com.github.liuweijw.business.admin.beans;

import com.github.liuweijw.system.api.model.AuthUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "用户信息扩展")
public class AuthUserExtendBean extends AuthUser {

	private static final long	serialVersionUID	= -7441853194277263314L;

	@ApiModelProperty("单位名称")
	private String				companyName;

	@ApiModelProperty("单位编码")
	private String				companyCode;

}

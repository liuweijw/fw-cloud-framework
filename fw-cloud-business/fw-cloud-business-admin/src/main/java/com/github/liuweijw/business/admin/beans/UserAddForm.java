package com.github.liuweijw.business.admin.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "新增|修改用户")
public class UserAddForm {

	@ApiModelProperty(value = "用户名，唯一 注：新增有效", required = true)
	private String	username;

	@ApiModelProperty(value = "用户编号，唯一 注：为空的情况走新增，不为空则走更新")
	private String	usercode;

	@ApiModelProperty(value = "用户密码 注：新增有效", required = true)
	private String	password;

	@ApiModelProperty("用户微信管理openId")
	private String	openId;

	@ApiModelProperty("用户个人图像")
	private String	picUrl;

	@ApiModelProperty("用户手机号码")
	private String	mobile;

	@ApiModelProperty(value = "用户所属单位编码", required = true)
	private String	companyCode;

	@ApiModelProperty("用户联系人名称")
	private String	contactName;

	@ApiModelProperty("用户联系mail")
	private String	email;

}

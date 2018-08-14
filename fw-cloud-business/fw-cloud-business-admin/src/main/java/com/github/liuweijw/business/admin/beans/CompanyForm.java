package com.github.liuweijw.business.admin.beans;

import com.github.liuweijw.commons.base.page.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "公司查询表单")
public class CompanyForm extends PageParams {

	@ApiModelProperty("单位名称")
	private String name;

}

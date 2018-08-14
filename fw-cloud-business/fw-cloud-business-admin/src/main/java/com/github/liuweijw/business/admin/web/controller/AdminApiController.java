package com.github.liuweijw.business.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.admin.beans.UserAddForm;
import com.github.liuweijw.business.admin.domain.Company;
import com.github.liuweijw.business.admin.domain.User;
import com.github.liuweijw.business.admin.service.CompanyService;
import com.github.liuweijw.business.admin.service.RoleService;
import com.github.liuweijw.business.admin.service.UserService;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.utils.RandomHelper;
import com.github.liuweijw.commons.utils.StringHelper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

/**
 * 无须经过网关权限的接口
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping("/api")
@ApiModel(description = "外部接口服务")
@PrePermissions(value = Module.API, required = false)
public class AdminApiController {

	@Autowired
	private UserService		userService;

	@Autowired
	private RoleService		roleService;

	@Autowired
	private CompanyService	companyService;

	@ApiOperation(value = "查询", notes = "查询所有单位数据")
	@RequestMapping(value = "/listAllCompany", method = RequestMethod.GET)
	public R<List<Company>> listAll(HttpServletRequest request) {
		List<Company> allList = this.companyService.findAllList();
		return new R<List<Company>>().data(allList).success();
	}

	/**
	 * 新增或修改
	 */
	@ApiOperation(value = "新增或修改", notes = "默认用户信息")
	@RequestMapping(value = "/addOrUpdUserInfo", method = RequestMethod.POST)
	public R<Boolean> addOrUpdUserInfo(HttpServletRequest request, @RequestBody UserAddForm userForm) {
		if (StringHelper.isBlank(userForm.getCompanyCode())) return new R<Boolean>().failure("所属单位不能为空");
		if (!StringHelper.isBlank(userForm.getUsercode())) { // 不为空 走更新
			User user = this.userService.findByUsercode(userForm.getUsercode());
			if (null == user) return new R<Boolean>().failure("用户编码不正确");
			user.setCompanyCode(userForm.getCompanyCode());
			user.setContactName(userForm.getContactName());
			user.setEmail(userForm.getEmail());
			user.setMobile(userForm.getMobile());
			user.setOpenId(userForm.getOpenId());
			user.setPicUrl(userForm.getPicUrl());

			boolean r = this.userService.updateUser(user);
			return new R<Boolean>().data(r);
		} else { // 新增逻辑
			if (StringHelper.isBlank(userForm.getUsername()))
				return new R<Boolean>().failure("用户登录名不能为空");

			User user = this.userService.findUserByUsername(userForm.getUsername().trim(), false);
			if (null != user) return new R<Boolean>().failure("用户登录名已经注册");

			user = new User();
			user.setUsername(userForm.getUsername().trim());
			user.setCreateTime(new Date());
			user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword().trim()));
			user.setDeptId(1); // 先默认写固定
			user.setStatu(0);
			user.setUsercode(RandomHelper.randomStringUpper());
			user.setUpdateTime(user.getCreateTime());
			user.setCompanyCode(userForm.getCompanyCode());
			user.setContactName(userForm.getContactName());
			user.setEmail(userForm.getEmail());
			user.setMobile(userForm.getMobile());
			user.setOpenId(userForm.getOpenId());
			user.setPicUrl(userForm.getPicUrl());

			return roleService.saveDefaultUserByRolecode(user, "ROLE_YANSHI");
		}
	}

}

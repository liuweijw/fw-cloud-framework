package com.github.liuweijw.business.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.liuweijw.commons.base.R;

/**
 * 微信商户信息获取
 * 
 * @author liuweijw
 */
@Controller
@RequestMapping("/wechat/material")
public class WxMaterialController {

	@Autowired
	private WxMpService	wxService;

	@RequestMapping(value = "getFile", method = RequestMethod.GET)
	public R<WxMpMaterialFileBatchGetResult> getFile(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			WxMpMaterialFileBatchGetResult newsResult = wxService.getMaterialService()
					.materialFileBatchGet("image", 0, 20);
			return new R<WxMpMaterialFileBatchGetResult>().data(newsResult).success();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return new R<WxMpMaterialFileBatchGetResult>().failure();
	}
}

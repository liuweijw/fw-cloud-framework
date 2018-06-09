package com.github.liuweijw.business.admin.web.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.liuweijw.business.admin.service.UserService;
import com.github.liuweijw.business.commons.permission.Module;
import com.github.liuweijw.business.commons.web.aop.PrePermissions;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.google.code.kaptcha.Producer;

/**
 * 图像验证码服务
 * 
 * @author liuweijw
 */
@Controller
@PrePermissions(value = Module.CODE, required = false)
public class CodeController {

	@Autowired
	private Producer	producer;

	@Autowired
	private UserService	userService;

	/**
	 * 创建验证码
	 */
	@GetMapping(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{randomStr}")
	public void createCode(@PathVariable String randomStr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		userService.saveImageCode(randomStr, text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		IOUtils.closeQuietly(out);
	}

}

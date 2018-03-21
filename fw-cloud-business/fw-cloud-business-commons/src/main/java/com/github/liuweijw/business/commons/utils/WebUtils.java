package com.github.liuweijw.business.commons.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.exception.CheckedException;

/**
 * Web相关工具类
 * 
 * @author liuweijw
 *
 */
public class WebUtils {

	public static void handleWithResponse(HttpServletResponse response, String responseStr) {
        PrintWriter printWriter;
		try {
			response.setCharacterEncoding(CommonConstant.UTF8);
	        response.setContentType(CommonConstant.CONTENT_TYPE);
			printWriter = response.getWriter();
			printWriter.append(responseStr);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CheckedException("Failed to response");
		}
	}
	
}

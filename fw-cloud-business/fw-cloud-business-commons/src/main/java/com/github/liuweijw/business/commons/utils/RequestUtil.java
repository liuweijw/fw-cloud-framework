package com.github.liuweijw.business.commons.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.exception.CheckedException;

/**
 * Web相关工具类
 * 
 * @author liuweijw
 */
public class RequestUtil {

	/**
	 * 判断是否是微信浏览器发出的请求
	 * 
	 * @param userAgent
	 */
	public static boolean isWxRequest(String userAgent) {
		return userAgent.indexOf("micromessenger") > -1;
	}

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

	public static String buildURLParams(String returnUrl, String filterKey) {
		if (StringHelper.isBlank(returnUrl)) return "";
		if (!returnUrl.contains("?")) return returnUrl;
		if (!returnUrl.contains("=")) return returnUrl.split("\\?")[0];
		if (!returnUrl.contains(filterKey + "=")) return returnUrl;
		String[] urls = returnUrl.split("\\?");
		String params[] = urls[1].split("&");
		StringBuilder returnUrlBuffer = new StringBuilder("");
		for (String param : params) {
			if (!param.contains("=")) continue;
			if (!param.startsWith(filterKey + "=")) returnUrlBuffer.append(param).append("&");
		}

		if (returnUrlBuffer.length() > 0) {
			returnUrlBuffer = returnUrlBuffer.deleteCharAt(returnUrlBuffer.length() - 1);
		}

		return urls[0] + "?" + returnUrlBuffer.toString();
	}

	public static String buildAppendURLParams(String returnUrl, String... params) {
		if (StringHelper.isBlank(returnUrl) || params.length == 0) return returnUrl;
		StringBuilder builder = new StringBuilder();
		builder.append(returnUrl).append((returnUrl.contains("?") ? "&" : "?"));
		for (String urlParam : params) {
			builder.append(urlParam).append("&");
		}
		if (builder.length() > 0) {
			builder = builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	public static String buildRequestBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
	}

}

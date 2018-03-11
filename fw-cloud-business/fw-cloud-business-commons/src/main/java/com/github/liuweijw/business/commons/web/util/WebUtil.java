package com.github.liuweijw.business.commons.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Web相关的工具类
 * 
 * @author liuweijw
 *
 */
public class WebUtil {

	public static void handleWithResponse(HttpServletResponse response, String data) {
		response.setContentType("text/x-json;charset=utf-8");
		response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		try {
			PrintWriter out = response.getWriter();
			out.write(data);
			out.close();
		} catch (IOException e) {
		}
	}
	
}

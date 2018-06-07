package com.github.liuweijw.business.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.liuweijw.commons.utils.StringHelper;

/**
 * 日期工具类
 * 
 * @author liuweijw
 */
public class DateUtils {

	private static final String	DEFAULT_CONVERT_PATTERN	= "yyyyMMddHHmmssSSS";

	/**
	 * 获取当前时间字符串(默认格式:yyyyMMddHHmmssSSS)
	 */
	public static String getCurrentTimeStrDefault() {
		return getCurrentTimeStr(DEFAULT_CONVERT_PATTERN);
	}

	/**
	 * 获取指定时间字符串(默认格式:yyyyMMddHHmmssSSS)
	 */
	public static String getTimeStrDefault(Date date) {
		if (null == date) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间字符串
	 *
	 * @param pattern
	 *            转换格式
	 */
	public static String getCurrentTimeStr(String pattern) {
		if (StringHelper.isBlank(pattern)) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	/**
	 * 获取指定时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date, String pattern) {
		if (null == date || StringHelper.isBlank(pattern)) return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 判断时间字符串是否为默认格式
	 * 
	 * @param dateTimeStr
	 */
	public static boolean isValidDefaultFormat(String dateTimeStr) {
		if (StringHelper.isBlank(dateTimeStr)) return false;

		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
		try {
			dateFormat.parse(dateTimeStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

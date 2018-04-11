package com.github.liuweijw.business.commons.web.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当该批注修饰一个方法时，在Controller切面中会对请求该方法的用户进行鉴权。 class 上面的权限 + "_" + method 上面的权限组合
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrePermissions {

	/**
	 * 仅适用于class类上进行注解 方法设置此属性无效
	 */
	boolean required() default true;

	/**
	 * 权限数组
	 */
	String[] value();
}

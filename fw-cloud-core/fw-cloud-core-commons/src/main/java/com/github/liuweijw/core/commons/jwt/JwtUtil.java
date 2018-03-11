package com.github.liuweijw.core.commons.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.utils.StringHelper;

/**
 * @author liuweijw
 * 
 * 用户相关工具类
 */
public class JwtUtil {
	
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    private static final ThreadLocal<String> THREAD_LOCAL_USER = new ThreadLocal<>();
    
    private static final String JWT_USER_NAME = "user_name";
    
    private static final String JWT_USER_AUTHORITIES = "authorities";
    
    /**
     * 根据用户请求中的token 获取用户名
     *
     * @param request Request
     * @return ""、username
     */
    public static String getUserName(HttpServletRequest request) {
        String authorization = request.getHeader(CommonConstant.REQ_HEADER);
        if (StringUtils.isEmpty(authorization)) return "";
        
        String token = StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
        if (StringUtils.isEmpty(token)) return "";
        
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return claims.get(JWT_USER_NAME).toString();
        } catch (Exception ex) {
            logger.error("用户名解析异常,token:{},key:{}", token, key);
        }
        
        return "";
    }

    /**
     * 通过token 获取用户名
     *
     * @param authorization token
     * @return 用户名
     */
    public static String getUserName(String authorization) {
        String token = StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
        if (StringUtils.isEmpty(token)) return "";
        
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return claims.get(JWT_USER_NAME).toString();
        } catch (Exception ex) {
            logger.error("用户名解析异常,token:{},key:{}", token, key);
        }
        
        return "";
    }

    /**
     * 根据请求heard中的token获取用户角色
     *
     * @param httpServletRequest request
     * @return 角色名
     */
    public static List<String> getRole(HttpServletRequest httpServletRequest) {
        return getRole(getToken(httpServletRequest));
    }

    /**
     * 根据请求heard中的token获取用户角色
     *
     * @param httpServletRequest request
     * @return 角色名
     */
    public static List<String> getRole(String token) {
    	if(StringHelper.isBlank(token)) return new ArrayList<String>();
    	String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        @SuppressWarnings("unchecked")
		List<String> roleCodes = (List<String>) claims.get(JWT_USER_AUTHORITIES);
        return roleCodes;
    }
    
    /**
     * 获取请求中token
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(CommonConstant.REQ_HEADER);
        if(StringHelper.isBlank(authorization)) return null;
        return StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
    }

    /**
     * 设置用户信息
     *
     * @param username 用户名
     */
    public static void setUser(String username) {
        THREAD_LOCAL_USER.set(username);

        MDC.put(CommonConstant.KEY_USER, username);
    }

    /**
     * 从threadlocal 获取用户名
     *
     * @return 用户名
     */
    public static String getUser() {
        return THREAD_LOCAL_USER.get();
    }

    /**
     * 如果没有登录，返回null
     *
     * @return 用户名
     */
    public static String getUserName() {
        return THREAD_LOCAL_USER.get();
    }

    /**
     * 清除所有
     */
    public static void clearAll() {
        THREAD_LOCAL_USER.remove();
        MDC.remove(CommonConstant.KEY_USER);
    }
}

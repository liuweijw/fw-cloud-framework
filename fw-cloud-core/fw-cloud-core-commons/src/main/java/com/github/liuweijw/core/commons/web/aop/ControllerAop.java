package com.github.liuweijw.core.commons.web.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.liuweijw.core.beans.system.AuthUser;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.commons.jwt.JwtUtil;

/**
 * @author liuweijw
 */
@Aspect
@Component
public class ControllerAop {
	
    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);
    
    @Autowired
    private CacheManager cacheManager;

    @Pointcut("execution(public com.github.liuweijw.core.utils.R *(..))")
    public void pointCutR() {
    }

    /**
     * 拦截器具体实现
     */
    @Around("pointCutR()")
    public Object methodRHandler(ProceedingJoinPoint pjp) {
        return methodHandler(pjp);
    }

    private Object methodHandler(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        HttpServletRequest request = attributes.getRequest();

        String token = JwtUtil.getToken(request);
        AuthUser authUser = null;
        if (StringUtils.isNotEmpty(token)) {
        	authUser = cacheManager.getCache(SecurityConstant.TOKEN_USER_DETAIL).get(token, AuthUser.class);
        }
        
        String username = "";
        if (null == authUser) {
            username = JwtUtil.getUserName(request);
            if (StringUtils.isNotEmpty(username)) JwtUtil.setUser(username);
        } else {
            username = authUser.getUsername();
            JwtUtil.setUser(username);
        }
        
        logger.info("Controller AOP get username:{}", username);
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(pjp.getArgs()));

        Object result;
        try {
            result = pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            logger.error("异常信息：", e);
            throw new RuntimeException(e);
        } finally {
            if (StringUtils.isNotEmpty(username)) {
            	JwtUtil.clearAll();
            }
        }
        return result;
    }
}

package com.github.liuweijw.gateway.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.MessageConstant;
import com.github.liuweijw.core.utils.R;
import com.github.liuweijw.exception.DeniedException;

/**
 * @author liuweijw
 * 
 * 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * 包装失败信息到DeniedException
 * 
 */
@Component
public class AccessDeniedHandler extends OAuth2AccessDeniedHandler {
	
    private static Logger logger = LoggerFactory.getLogger(AccessDeniedHandler.class);
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        logger.info("授权失败，禁止访问");
        response.setCharacterEncoding(CommonConstant.UTF8);
        response.setContentType(CommonConstant.CONTENT_TYPE);
        R<String> result = new R<String>().failure(new DeniedException(MessageConstant.COMMONS_AUTH_NOTSUPPORT));
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}

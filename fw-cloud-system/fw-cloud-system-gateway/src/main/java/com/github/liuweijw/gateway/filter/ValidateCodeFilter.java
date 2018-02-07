package com.github.liuweijw.gateway.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.SecurityConstants;
import com.github.liuweijw.core.utils.R;
import com.github.liuweijw.core.utils.StringHelper;
import com.github.liuweijw.exception.ValidateCodeException;

/**
 * @author liuweijw
 * 
 * 验证码校验，true开启，false关闭校验
 * 更细化可以 clientId 进行区分
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {
    
	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeFilter.class);
    
	@Value("${security.validate.code:true}")
    private boolean isValidate;
    
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
    
	@Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isValidate && (StringHelper.contains(request.getRequestURI(), SecurityConstants.OAUTH_TOKEN_URL)
                || StringHelper.contains(request.getRequestURI(), SecurityConstants.REFRESH_TOKEN)
                || StringHelper.contains(request.getRequestURI(), SecurityConstants.MOBILE_TOKEN_URL))) {
            PrintWriter printWriter = null;
            try {
                checkCode(request, response, filterChain);
            } catch (ValidateCodeException e) {
                logger.info("登录失败：{}", e.getMessage());
                response.setCharacterEncoding(CommonConstant.UTF8);
                response.setContentType(CommonConstant.CONTENT_TYPE);
                R<String> result = new R<String>().failure(e);
                response.setStatus(478);
                printWriter = response.getWriter();
                printWriter.append(objectMapper.writeValueAsString(result));
            } finally {
                IOUtils.closeQuietly(printWriter);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @SuppressWarnings("unchecked")
	private void checkCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String code = httpServletRequest.getParameter("code");
        String randomStr = httpServletRequest.getParameter("randomStr");
        if (StringHelper.isBlank(randomStr)) {
            randomStr = httpServletRequest.getParameter("mobile");
        }
        Object codeObj = redisTemplate.opsForValue().get(SecurityConstants.DEFAULT_CODE_KEY + randomStr);

        if (codeObj == null) {
            throw new ValidateCodeException("验证码为空或已过期");
        }
        String saveCode = codeObj.toString();

        if (StringHelper.isBlank(code)) {
            redisTemplate.delete(SecurityConstants.DEFAULT_CODE_KEY + randomStr);
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (StringHelper.isEmpty(saveCode)) {
            redisTemplate.delete(SecurityConstants.DEFAULT_CODE_KEY + randomStr);
            throw new ValidateCodeException("验证码已过期或已过期");
        }

        if (!StringHelper.equals(saveCode, code)) {
            redisTemplate.delete(SecurityConstants.DEFAULT_CODE_KEY + randomStr);
            throw new ValidateCodeException("验证码不匹配");
        }

        if (StringHelper.equals(code, saveCode)) {
            redisTemplate.delete(SecurityConstants.DEFAULT_CODE_KEY + randomStr);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

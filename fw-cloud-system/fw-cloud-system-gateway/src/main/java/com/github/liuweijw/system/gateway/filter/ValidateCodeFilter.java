package com.github.liuweijw.system.gateway.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.exception.ValidateCodeException;

/**
 * 验证码校验，true开启，false关闭校验 更细化可以 clientId 进行区分
 * 
 * @author liuweijw
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {

	/**
	 * 根据业务是否需要对验证码进行验证
	 */
	@Value("${security.validate.code:false}")
	private boolean			isValidate;

	/**
	 * redis 操作工具
	 */
	@Autowired
	@SuppressWarnings("rawtypes")
	private RedisTemplate	redisTemplate;

	/**
	 * JOSN OBJECT 操作工具
	 */
	@Autowired
	private ObjectMapper	objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		if (isValidate
				&& (StringUtils.contains(request.getRequestURI(), SecurityConstant.OAUTH_TOKEN_URL) || StringUtils
						.contains(request.getRequestURI(), SecurityConstant.MOBILE_TOKEN_URL))) {
			PrintWriter printWriter = null;
			try {
				checkCode(request, response, filterChain);
			} catch (ValidateCodeException e) {
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
	private void checkCode(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException,
			ServletException {
		String code = httpServletRequest.getParameter("code");
		if (StringUtils.isBlank(code)) { throw new ValidateCodeException("请输入验证码"); }

		String randomStr = httpServletRequest.getParameter("randomStr");
		if (StringUtils.isBlank(randomStr)) {
			randomStr = httpServletRequest.getParameter("mobile");
		}

		String key = SecurityConstant.DEFAULT_CODE_KEY + randomStr;
		if (!redisTemplate.hasKey(key)) { throw new ValidateCodeException("验证码已过期，请重新获取"); }

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) { throw new ValidateCodeException("验证码已过期，请重新获取"); }

		String saveCode = codeObj.toString();
		if (StringUtils.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("验证码已过期，请重新获取");
		}

		if (!StringUtils.equals(saveCode, code)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("验证码错误，请重新输入");
		}

		redisTemplate.delete(key);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}

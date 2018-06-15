package com.github.liuweijw.system.auth.component.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.xiaoleilu.hutool.map.MapUtil;

/**
 * @author liuweijw
 */
@Slf4j
@Component
public class AjaxLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper						objectMapper;

	@Autowired
	private ClientDetailsService				clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices	authorizationServerTokenServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String header = request.getHeader(SecurityConstant.AUTHORIZATION);
		if (StringHelper.isBlank(header) || !header.startsWith(SecurityConstant.BASIC))
			throw new UnapprovedClientAuthenticationException("请求头中client信息为空");

		try {
			String[] tokens = extractAndDecodeHeader(header);
			assert tokens.length == 2;
			String clientId = tokens[0];

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
			TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), CommonConstant.SPRING_SECURITY_FORM_MOBILE_KEY);
			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
			OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
			log.info("获取token 成功：{}", oAuth2AccessToken.getValue());

			response.setCharacterEncoding(CommonConstant.UTF8);
			response.setContentType(CommonConstant.CONTENT_TYPE);
			PrintWriter printWriter = response.getWriter();
			printWriter.append(objectMapper.writeValueAsString(oAuth2AccessToken));
		} catch (IOException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}
	}

	private String[] extractAndDecodeHeader(String header) throws IOException {

		byte[] base64Token = header.substring(6)
				.getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, CommonConstant.UTF8);

		int delim = token.indexOf(":");

		if (delim == -1) { throw new BadCredentialsException("Invalid basic authentication token"); }
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
}

package com.github.liuweijw.system.auth.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.configuration.JwtConfiguration;

/**
 * @author liuweijw 认证服务器逻辑实现
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class FwAuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthServerConfiguration	authServerConfiguration;

	@Autowired
	private JwtConfiguration		jwtConfiguration;

	@Autowired
	private AuthenticationManager	authenticationManager;

	@Autowired
	private UserDetailsService		userDetailsService;

	@Autowired
	private RedisConnectionFactory	redisConnectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(authServerConfiguration.getClientId())
				.secret(authServerConfiguration.getClientSecret())
				.authorizedGrantTypes(SecurityConstant.REFRESH_TOKEN, SecurityConstant.PASSWORD, SecurityConstant.AUTHORIZATION_CODE)
				.scopes(authServerConfiguration.getScope())
				// true 直接跳转到客户端页面，false 跳转到用户确认授权页面
				.autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
		endpoints.tokenStore(redisTokenStore())
				.tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager)
				.reuseRefreshTokens(false)
				.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()
				// 获取JWt加密key: /oauth/token_key 采用RSA非对称加密时候使用。对称加密禁止访问
				// .tokenKeyAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		FwJwtAccessTokenConverter jwtAccessTokenConverter = new FwJwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(jwtConfiguration.getJwtkey());
		// log.info("Initializing JWT with public key:\n" + authServerConfiguration.getPublicKey());

		// 采用RSA非对称加密
		// JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		// jwtAccessTokenConverter.setSigningKey(authServerConfiguration.getPrivateKey());
		// jwtAccessTokenConverter.setVerifierKey(authServerConfiguration.getPublicKey());
		return jwtAccessTokenConverter;
	}

	/**
	 * tokenstore 定制化处理 1. 如果使用的 redis-cluster 模式请使用 FwRedisTokenStore FwRedisTokenStore tokenStore = new
	 * FwRedisTokenStore();
	 * tokenStore.setRedisTemplate(redisTemplate);
	 */
	@Bean
	public TokenStore redisTokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstant.PREFIX);
		return tokenStore;
	}

	/**
	 * jwt 生成token 定制化处理
	 * 
	 * @return TokenEnhancer
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(1);
			additionalInfo.put("license", SecurityConstant.LICENSE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}

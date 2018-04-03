package com.github.liuweijw.system.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.github.liuweijw.core.commons.constants.CommonConstant;
import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.system.auth.component.FwWebResponseExceptionTranslator;

/**
 * @author liuweijw
 *
 * 认证服务器逻辑实现
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class FwAuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthServerConfiguration authServerConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private FwWebResponseExceptionTranslator fwWebResponseExceptionTranslator;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(authServerConfiguration.getClientId())
                .secret(authServerConfiguration.getClientSecret())
                .authorizedGrantTypes(SecurityConstant.REFRESH_TOKEN, SecurityConstant.PASSWORD,SecurityConstant.AUTHORIZATION_CODE)
                .scopes(authServerConfiguration.getScope())
                // true 直接跳转到客户端页面，false 跳转到用户确认授权页面
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .exceptionTranslator(fwWebResponseExceptionTranslator)
                .reuseRefreshTokens(false)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                // 获取JWt加密key: /oauth/token_key 采用RSA非对称加密时候使用。对称加密禁止访问
                //.tokenKeyAccess("isAuthenticated()") 
                .checkTokenAccess("permitAll()");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(CommonConstant.SIGN_KEY);
    	// log.info("Initializing JWT with public key:\n" + authServerConfiguration.getPublicKey());
        
        // 采用RSA非对称加密
        //JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwtAccessTokenConverter.setSigningKey(authServerConfiguration.getPrivateKey());
        //jwtAccessTokenConverter.setVerifierKey(authServerConfiguration.getPublicKey());
        return jwtAccessTokenConverter;
    }

}

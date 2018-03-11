package com.github.liuweijw.system.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.github.liuweijw.core.utils.R;

@RestController
public class AuthController {

	@Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @RequestMapping("/user")
    public Object user(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 清除Redis中 accesstoken refreshtoken
     */
    @PostMapping("/removeToken")
    @CacheEvict(value = SecurityConstant.TOKEN_USER_DETAIL, key = "#accesstoken")
    public R<Boolean> removeToken(String accesstoken, String refreshToken) {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.removeRefreshToken(refreshToken);
        tokenStore.removeAccessToken(accesstoken);
        return new R<Boolean>().data(Boolean.TRUE);
    }
}

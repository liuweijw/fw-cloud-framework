package com.github.liuweijw.system.gateway.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author liuweijw
 * 
 * 在RateLimitPreFilter 之前执行，否则会出现空指针问题
 */
@Component
public class AccessFilter extends ZuulFilter {

	@Autowired
    private RedisConnectionFactory redisConnectionFactory;
	
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

	@Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());
        
        /*String token = JwtUtil.getToken(ctx.getRequest());
        if(null == token){
        	ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!"anonymousUser".equals(authentication.getPrincipal().toString())) {
        	RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if(null == accessToken || accessToken.isExpired()){
            	ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                return null;
            }
        }*/
        
        return null;
    }
}

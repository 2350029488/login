package com.huanglong.login.filter;

import com.alibaba.fastjson.JSON;
import com.huanglong.login.utils.JwtUtil;
import com.huanglong.login.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * 过滤器的自定义配置
 *
 * 这里可以做权限认证？ security?
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
     @Autowired
    private RedisUtil redisUtil;
      @Autowired
    private NotAuthenticateUrlProperties NotAuthenticateUrlProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                String currentUrl = exchange.getRequest().getURI().getPath();
        System.out.println(currentUrl);
        //设置不用认证的请求直接放行
        if (shouldSkip(currentUrl)) {
            log.info("=====返回已跳过url{}===", currentUrl);
            return chain.filter(exchange);
        }else {
            //不是不用认证的请求，通过是否携带token进行认证
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if (token==null){
                throw new ArrayIndexOutOfBoundsException("token为null");
            }
            System.out.println("开始");
            System.out.println(token);
            System.out.println("结束");
          String userId;
            try {
                Claims  claims = JwtUtil.parseJWT(token);
                userId= claims.getSubject();
            } catch (Exception e) {
                //出现异常直接退出
                e.printStackTrace();
               throw new RuntimeException("token非ss法");
            }
            //从中间件redis中进行获取登录信息
            String getRedisLoginUser = JSON.parseObject(redisUtil.getCacheObject("test_id:" + userId), String.class);
           //没有表示此用户没有登录
            if(getRedisLoginUser == null)
            {
                throw new RuntimeException("用户未登录");
            }
            return chain.filter(exchange);
        }
    }



    @Override
    public int getOrder() {
        return 0;
    }

        private boolean shouldSkip(String currentUrl) {
        //路径匹配器 匹配成功则返回true
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String skipPath : NotAuthenticateUrlProperties.getShouldSkipUrls()) {
            if (pathMatcher.match(skipPath, currentUrl)) {
                return true;
            }
        }
        return false;
    }
}

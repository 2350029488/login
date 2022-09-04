package com.huanglong.login.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.huanglong.login.utils.JwtUtil;
import com.huanglong.login.utils.LoginUser;
import com.huanglong.login.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (StrUtil.hasBlank(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
             String id;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id=claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("过滤器处认证：token非法");
        }
        LoginUser loginUser = JSON.parseObject(redisUtil.getCacheObject("test_id:" + id), LoginUser.class);
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

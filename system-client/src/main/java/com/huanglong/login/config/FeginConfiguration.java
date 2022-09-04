package com.huanglong.login.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *  把请求头token设置在拦截器中
 */
@Component
public class FeginConfiguration {
    @Bean
    public RequestInterceptor headerInterceptor(){
        return requestTemplate -> {
            ServletRequestAttributes    attributes=
     (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes==null){
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            if (!StringUtils.isEmpty(token)){
                requestTemplate.header("token",token);
            }
        };
        }
}

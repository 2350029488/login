package com.huanglong.login.filter;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * 不需要认证的请求
 */

@Data
@Component
@ConfigurationProperties("user.exclude")
public class NotAuthenticateUrlProperties {

    private LinkedHashSet<String> shouldSkipUrls;
}

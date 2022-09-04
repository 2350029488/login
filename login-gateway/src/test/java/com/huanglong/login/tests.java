package com.huanglong.login;

import com.alibaba.fastjson.JSON;
import com.huanglong.login.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class tests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void ets(){
        redisUtil.setCacheObject("kkk", JSON.toJSONString("33333333"));
        System.out.println(JSON.parseObject(redisUtil.getCacheObject("kkk"),String.class));
    }

}

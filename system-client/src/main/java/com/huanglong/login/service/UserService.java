package com.huanglong.login.service;

import com.huanglong.login.entity.User;
import com.huanglong.login.utils.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "gateway-server")
public interface UserService {
    @DeleteMapping("/user/delete")
    public Message delete();
    @PostMapping("/user/register")
    public Message register(@RequestBody User user) ;
    @PostMapping("/user/login")
    public Message login(@RequestBody User user);

}

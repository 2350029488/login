package com.huanglong.login.controller;

import com.huanglong.login.entity.User;
import com.huanglong.login.service.UserService;
import com.huanglong.login.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class Usercontroller {

    @Autowired
    private UserService userService;

    @GetMapping("/client/delete")
    public Message delete(){
       return userService.delete();
    }
    @PostMapping("/client/login")
    public Message login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/client/register")
    public Message register(@RequestBody User user){
        return userService.register(user);
    }
}

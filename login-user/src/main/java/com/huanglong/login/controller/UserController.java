package com.huanglong.login.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huanglong.login.entity.User;
import com.huanglong.login.service.IUserService;
import com.huanglong.login.utils.JwtUtil;
import com.huanglong.login.utils.LoginUser;
import com.huanglong.login.utils.Message;
import com.huanglong.login.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄隆
 * @since 2022-09-03
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
   private RedisUtil redisUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Message login(@RequestBody User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                =new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw  new RuntimeException("登录失败");
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        Integer id = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(String.valueOf(id));
        String redisLoginUser = JSON.toJSONString(loginUser);
        redisUtil.setCacheObject("test_id:"+id,redisLoginUser);
        return Message.success().add("token",token);

    }

    @PostMapping("/register")
    public Message register(@RequestBody User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        System.out.println(user);
        boolean save = userService.save(user);
        if (save){
            return Message.success();
        }else {
            return Message.fail();
        }
    }


    @DeleteMapping("/delete")
    public Message delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer getId = loginUser.getUser().getId();
        log.info("id为:{}",getId);

        boolean b = userService.removeById(getId);
        redisUtil.deleteObject("test_id:"+getId);
        return Message.success().add("ok?",b);

    }

    @GetMapping("/select")
    public String mehtod3(@RequestParam("username") String username) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> eq = queryWrapper.eq(User::getUsername, username);
        List<User> list = userService.list(eq);
        return list.toString();
    }
}

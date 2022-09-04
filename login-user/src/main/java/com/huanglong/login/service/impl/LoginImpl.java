package com.huanglong.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huanglong.login.entity.User;
import com.huanglong.login.service.IUserService;
import com.huanglong.login.utils.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginImpl implements UserDetailsService {
@Autowired
private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> eq = queryWrapper.eq(User::getUsername, username);
        User getUser = userService.getOne(eq);
        if (StringUtils.isNotBlank(getUser.toString())){
            UserDetails loginUser = new LoginUser(getUser);
           return loginUser;
        }else {
            throw new UsernameNotFoundException("用户名错误");
        }


    }
}

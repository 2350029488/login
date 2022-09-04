package com.huanglong.login.service.impl;

import com.huanglong.login.entity.User;
import com.huanglong.login.mapper.UserMapper;
import com.huanglong.login.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄隆
 * @since 2022-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

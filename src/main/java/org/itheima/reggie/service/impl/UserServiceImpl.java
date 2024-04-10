package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.entity.User;
import org.itheima.reggie.mapper.UserMapper;
import org.itheima.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

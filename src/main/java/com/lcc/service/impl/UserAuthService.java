package com.lcc.service.impl;

import com.lcc.mapper.UserMapper;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: UserAuthService
 * Package: com.lcc.controller.api
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/20 18:54
 * @Version
 */
@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.lcc.entity.User user = userMapper.getInfoByUsername(s);
        if (user == null) throw new UsernameNotFoundException("登录失败，用户名或密码错误！");

        return User
                .withUsername(s)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}

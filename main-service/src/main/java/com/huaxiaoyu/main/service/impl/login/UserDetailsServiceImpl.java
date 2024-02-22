package com.huaxiaoyu.main.service.impl.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huaxiaoyu.main.domain.LoginUser;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (user == null)
            throw new RuntimeException("用户名或密码错误");
        

        //TODO 根据用户查询权限信息 添加到LoginUser中
        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("admin");

        return new LoginUser(user, list);
    }
}

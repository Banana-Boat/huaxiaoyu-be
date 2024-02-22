package com.huaxiaoyu.main.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.mapper.UserMapper;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.util.JwtUtil;
import com.huaxiaoyu.main.util.R;
import com.huaxiaoyu.main.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Base64;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(User user) {
        String pd = user.getPassword();
        String username = user.getUsername();

        User user1 = userMapper.getByName(username);
        if (user1 == null)
            return new R("用户名不存在，登陆失败", false);

        if (!pd.equals(user1.getPassword()))
            return new R("密码错误，登陆失败", false);

        user1.setPassword(null);
        return new R("登陆成功", user1, true);
    }

    @Override
    public R logout() {
        return null;
    }

    @Override
    public User getByName(String username) {
        return userMapper.getByName(username);
    }

    @Override
    public IPage<User> queryByPage(int currentPage, int pageSize, String search) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(search))
            wrapper.like(User::getUsername, search);

        IPage<User> bookIPage = new Page<>(currentPage, pageSize);
        return userMapper.selectPage(bookIPage, wrapper);
    }

    @Override
    public Boolean checkNameUnique(String username) {
        User user = userMapper.getByName(username);
        return user == null;
    }

    @Override
    public R changeInfo(User user) {
        User user0 = userMapper.getByName(user.getUsername());
        if (user0 == null)
            return new R("用户名不存在,修改失败", false);

        String data_base64 = user.getHeadPhoto();
        if (data_base64 != null) {
            byte[] b = Base64.getDecoder().decode(data_base64);
            String uuid = IdUtil.fastSimpleUUID(); // 文件唯一标识
            String rootFilePath = System.getProperty("user.dir") + "/user/files/" + uuid + "_" + user.getUsername() + ".jpg";
            File rootFile = new File(rootFilePath);
            if (!rootFile.getParentFile().exists())
                rootFile.getParentFile().mkdirs();

            String url = "localhost:" + serverPort + "/user/files/" + uuid + "_" + user.getUsername() + ".jpg";
            FileUtil.writeBytes(b, rootFilePath);
            user.setHeadPhoto(url);
        }

        user.setChangeTime(new Date());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        int flag = userMapper.update(user, wrapper);
        User res = userMapper.getByName(user.getUsername());
        res.setPassword(null);
        if (flag != 1)
            return new R("修改失败", false);

        return new R("修改成功", res, true);
    }

    @Override
    public R getUserInfo(String authorization) {
        if (!org.springframework.util.StringUtils.hasText(authorization))
            return new R("token非法", false);

        /* 解析token */
        String userid;
        String token = null;
        try {
            String[] tokens = authorization.split(" ");
            if (tokens.length > 1)
                token = tokens[1];

            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return new R("token非法", false);
        }

        User user = userMapper.selectById(userid);
        user.setPassword(null);

        return new R("用户信息获取成功！", user, true);
    }
}

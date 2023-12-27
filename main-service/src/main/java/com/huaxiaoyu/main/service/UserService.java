package com.huaxiaoyu.main.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.util.R;

public interface UserService extends IService<User> {
    public R login(User user);

    public R logout();

    User getByName(String username);

    IPage<User> queryByPage(int currentPage, int pageSize, String search);

    public Boolean checkNameUnique(String username);

    public R changeInfo(User user);
    
    public R getUserInfo(String authorization);


}

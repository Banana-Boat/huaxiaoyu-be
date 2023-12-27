package com.huaxiaoyu.main.service.login;


import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.util.R;


public interface LoginServcie {
    R login(User user);

    R logout();

}

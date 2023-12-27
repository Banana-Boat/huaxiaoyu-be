package com.huaxiaoyu.main.service.friend;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.friend.Friend;

import java.util.List;

public interface FriendService extends IService<Friend> {
    public List<User> get();

    public Boolean isFriend(Integer AId, Integer BId);

    public Boolean delete(Integer BId);

    public Boolean add(Integer BId);

}

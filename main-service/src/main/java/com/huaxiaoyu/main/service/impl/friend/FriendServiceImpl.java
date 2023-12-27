package com.huaxiaoyu.main.service.impl.friend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaxiaoyu.main.domain.LoginUser;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.friend.Friend;
import com.huaxiaoyu.main.mapper.UserMapper;
import com.huaxiaoyu.main.mapper.friend.FriendMapper;
import com.huaxiaoyu.main.service.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {
    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> get() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getId();
        List<User> res = new ArrayList<>();
        LambdaQueryWrapper<Friend> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Friend::getSendId, userId);
        List<Friend> friends_1 = friendMapper.selectList(wrapper1);
        for (Friend f : friends_1) {
            if (f.getStatus() == 1) {
                User user = userMapper.selectById(f.getReceiveId());
                user.setPassword(null);
                res.add(user);
            }
        }
        LambdaQueryWrapper<Friend> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Friend::getReceiveId, userId);
        List<Friend> friends_2 = friendMapper.selectList(wrapper2);
        for (Friend f : friends_2) {
            if (f.getStatus() == 1) {
                User user = userMapper.selectById(f.getSendId());
                user.setPassword(null);
                res.add(user);
            }
        }
        return res;
    }

    @Override
    public Boolean isFriend(Integer AId, Integer BId) {
        //朋友判断
        boolean isfriend = false;
        LambdaQueryWrapper<Friend> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Friend::getSendId, AId);
        wrapper1.eq(Friend::getReceiveId, BId);
        wrapper1.eq(Friend::getStatus, 1);
        List<Friend> isfriend1 = friendMapper.selectList(wrapper1);

        LambdaQueryWrapper<Friend> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Friend::getSendId, BId);
        wrapper2.eq(Friend::getReceiveId, AId);
        wrapper2.eq(Friend::getStatus, 1);
        List<Friend> isfriend2 = friendMapper.selectList(wrapper2);

        if (!isfriend1.isEmpty() || !isfriend2.isEmpty()) {
            isfriend = true;
        }
        return isfriend;
    }

    @Override
    public Boolean delete(Integer BId) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer AId = loginUser.getUser().getId();

        LambdaQueryWrapper<Friend> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Friend::getSendId, AId);
        wrapper1.eq(Friend::getReceiveId, BId);
        wrapper1.eq(Friend::getStatus, 1);
        List<Friend> isfriend1 = friendMapper.selectList(wrapper1);
        for (Friend friends : isfriend1) {
            friendMapper.deleteById(friends.getId());
        }

        LambdaQueryWrapper<Friend> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Friend::getSendId, BId);
        wrapper2.eq(Friend::getReceiveId, AId);
        wrapper2.eq(Friend::getStatus, 1);
        List<Friend> isfriend2 = friendMapper.selectList(wrapper2);
        for (Friend friends : isfriend2) {
            friendMapper.deleteById(friends.getId());
        }
        return true;
    }

    @Override
    public Boolean add(Integer BId) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer AId = loginUser.getUser().getId();

        Friend f = new Friend();
        f.setSendId(AId);
        f.setReceiveId(BId);
        f.setStatus(1);
        f.setCreateTime(new Date());
        int flag = friendMapper.insert(f);
        if (flag == 1) {
            return true;
        }
        return false;
    }
}

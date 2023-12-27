package com.huaxiaoyu.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaxiaoyu.main.domain.LoginUser;
import com.huaxiaoyu.main.domain.Message;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.mapper.MessageMapper;
import com.huaxiaoyu.main.mapper.UserMapper;
import com.huaxiaoyu.main.service.MessageService;
import com.huaxiaoyu.main.service.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendService friendService;


    @Override
    public List<HashMap<String, Object>> get_records() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getId();


        List<User> users = new ArrayList<>();
        List<Integer> users_id = new ArrayList<>();


        List<HashMap<String, Object>> res_final = new ArrayList<>();
        LambdaQueryWrapper<Message> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Message::getSendId, userId);
        wrapper1.orderByDesc(Message::getId);
        List<Message> message_send = messageMapper.selectList(wrapper1);


        LambdaQueryWrapper<Message> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Message::getReceiveId, userId);
        wrapper2.orderByDesc(Message::getId);
        List<Message> message_receive = messageMapper.selectList(wrapper2);

        for (Message message : message_receive) {
            User user = userMapper.selectById(message.getSendId());
            if (!users_id.contains(user.getId())) {
                users.add(user);
                users_id.add(user.getId());
                HashMap<String, Object> res = new HashMap<>();
                user.setPassword(null);
                res.put("opponent", user);
                res.put("createdAt", message.getCreateTime());
                res.put("recordId", message.getId());
                res.put("isFriend", friendService.isFriend(userId, user.getId()));
                res_final.add(res);
            }
        }
        for (Message message : message_send) {
            User user = userMapper.selectById(message.getReceiveId());
            if (!users_id.contains(user.getId())) {
                users.add(user);
                HashMap<String, Object> res = new HashMap<>();
                user.setPassword(null);
                res.put("opponent", user);
                res.put("createdAt", message.getCreateTime());
                res.put("recordId", message.getId());
                res.put("isFriend", friendService.isFriend(userId, user.getId()));
                res_final.add(res);
            }
        }
        return res_final;
    }
}

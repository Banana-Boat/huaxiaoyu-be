package com.huaxiaoyu.main.service.impl;

import com.huaxiaoyu.main.service.StartChatService;
import com.huaxiaoyu.main.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartChatServiceImpl implements StartChatService {
    @Autowired
    private RedisCache redisCache;

    @Override
    public String startChat(Integer aid, Integer bid) {
        System.out.println("start chat: " + aid + " " + bid);
        String a = aid.toString();
        String b = bid.toString();
        redisCache.setCacheObject(a, b);
        redisCache.setCacheObject(b, a);
        
        return "start chat success";
    }
}

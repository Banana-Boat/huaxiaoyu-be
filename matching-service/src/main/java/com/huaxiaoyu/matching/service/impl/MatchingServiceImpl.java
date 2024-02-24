package com.huaxiaoyu.matching.service.impl;


import com.huaxiaoyu.matching.service.MatchingService;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, String sex) {
        System.out.println("try adding player: " + userId + "   sex: " + sex);
        matchingPool.addPlayer(userId, sex);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("try removing player: " + userId);
        matchingPool.removePlayer(userId);
        return "remove player success";
    }
}

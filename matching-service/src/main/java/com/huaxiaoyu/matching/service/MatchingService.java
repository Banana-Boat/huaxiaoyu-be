package com.huaxiaoyu.matching.service;

public interface MatchingService {
    String addPlayer(Integer userId, String sex);

    String removePlayer(Integer userId);
}

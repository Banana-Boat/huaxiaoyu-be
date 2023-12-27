package com.huaxiaoyu.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huaxiaoyu.main.domain.Interest;

import java.util.HashMap;
import java.util.List;

public interface InterestService extends IService<Interest> {
    HashMap<String, List<Interest>> getInterestDicts();
}

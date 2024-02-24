package com.huaxiaoyu.main.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "matching-service")
public interface MatchingFeignClient {
    @PostMapping("/match/user/add/")
    String addUser(MultiValueMap<String, String> data);

    @PostMapping("/match/user/remove/")
    String removeUser(MultiValueMap<String, String> data);
}

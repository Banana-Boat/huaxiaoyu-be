package com.huaxiaoyu.matching.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "main-service")
public interface MainFeignClient {
    @PostMapping("/user/start-chatting")
    void startChat(MultiValueMap<String, String> data);
}

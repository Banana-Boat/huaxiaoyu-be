package com.huaxiaoyu.matching.client;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CrushingClient {
    @PostMapping("/user/startchating")
    public void startchat(MultiValueMap<String, String> data);

    @GetMapping("test/test2")
    String test();
}

package com.huaxiaoyu.main.client;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@FeignClient(value = "crushingsystem",configuration = FeignConfig.class)
//@FeignClient("crushingsystem")
@RestController
public interface HuaxiaoyuClient {
    @PostMapping("/match/user/add/")
    String addUser(MultiValueMap<String, String> data);

    @PostMapping("/match/user/remove/")
    String removeUser(MultiValueMap<String, String> data);

    @GetMapping("/test")
    String test();
}

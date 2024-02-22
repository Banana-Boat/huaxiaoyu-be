package com.huaxiaoyu.main.controller.chat;

import com.huaxiaoyu.main.consumer.Consumer;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.service.StartChatService;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.util.R;
import com.huaxiaoyu.main.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@RestController
public class ChatController {
    @Autowired
    private UserService userService;

    @Autowired
    private StartChatService startChatService;

    @Autowired
    private Util util;

    @PostMapping("/user/start-chatting")
    public R startGame(@RequestParam MultiValueMap<String, String> data) {
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        User userA = userService.getById(aId);
        User userB = userService.getById(bId);
        HashMap<String, String> res = new HashMap<>();
        res.put("userA", userA.getUsername());
        res.put("userB", userB.getUsername());
        String s = startChatService.startChat(aId, bId);
        System.out.println(s);
        return new R("开始聊天", res, true);
    }

    @GetMapping("/chat/getNumOfOnline")
    public R get() {
        int size = Consumer.users.size();
        HashMap<String, Integer> res = new HashMap<>();
        res.put("num", size);
        return new R("当前在线人数", res, true);
    }

    @GetMapping("/chat/getRandomUserInfo/{num}")
    public R get_users(@PathVariable("num") Integer num) {
        int k = 0;
        List<User> res = new ArrayList<>();
        List<User> users = userService.list();
        List<Integer> indexs = util.getRandomN(num, users.size());
        for (Integer index : indexs) {
            res.add(users.get(index));
        }

        HashMap<String, List<User>> res_final = new HashMap<>();
        res_final.put("userList", res);
        return new R("当前人员信息", res_final, true);
    }

}

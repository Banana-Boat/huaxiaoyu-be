package com.huaxiaoyu.main.controller.chat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.chat.AssistedChat;
import com.huaxiaoyu.main.mapper.chat.AssistedChatMapper;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.util.R;
import com.huaxiaoyu.main.util.Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class AssistedChatController {
    @Autowired
    private AssistedChatMapper assistedChatMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private Util util;

    @GetMapping("getRecommendedTopics")
    public R get(@Param("receiveId") Integer receiveId,
                 @Param("sendId") Integer sendId,
                 @Param("num") Integer num
    ) {
        User user1 = userService.getById(receiveId);
        if (user1 == null) {
            return new R("用户id不存在", false);
        }
        String interestCodeList1 = user1.getInterestCodeList();
        int s1 = 0, s2 = 0, e1 = 0;

        User user2 = userService.getById(sendId);
        String interestCodeList2 = user2.getInterestCodeList();
        if (interestCodeList1.contains("1111") && interestCodeList2.contains("1111")) {
            s1 = 1;
        }
        if (interestCodeList1.contains("2222") && interestCodeList2.contains("2222")) {
            s2 = 1;
        }
        if (interestCodeList1.contains("3333") && interestCodeList2.contains("3333")) {
            e1 = 1;
        }
        if (s1 == 0 && s2 == 0 && e1 == 0) {
            if (interestCodeList1.contains("1111") || interestCodeList2.contains("1111")) {
                s1 = 1;
            }
            if (interestCodeList1.contains("2222") || interestCodeList2.contains("2222")) {
                s2 = 1;
            }
            if (interestCodeList1.contains("3333") || interestCodeList2.contains("3333")) {
                e1 = 1;
            }
        }

        List<AssistedChat> res = new ArrayList<>();
        List<AssistedChat> res1 = new ArrayList<>();
        List<AssistedChat> res2 = new ArrayList<>();
        List<AssistedChat> res3 = new ArrayList<>();
        if (s1 == 1) {
            LambdaQueryWrapper<AssistedChat> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AssistedChat::getType, "sport");
            wrapper.orderByDesc(AssistedChat::getId);
            res1 = assistedChatMapper.selectList(wrapper);
        }
        if (s2 == 1) {
            LambdaQueryWrapper<AssistedChat> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AssistedChat::getType, "study");
            wrapper.orderByDesc(AssistedChat::getId);
            res2 = assistedChatMapper.selectList(wrapper);
        }
        e1 = 1;
        if (e1 == 1) {
            LambdaQueryWrapper<AssistedChat> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AssistedChat::getType, "entertainment");
            wrapper.orderByDesc(AssistedChat::getId);
            res3 = assistedChatMapper.selectList(wrapper);
        }

        for (AssistedChat s : res1) {
            s.setType("运动");
            res.add(s);
        }
        for (AssistedChat s : res2) {
            s.setType("学习");
            res.add(s);
        }
        for (AssistedChat s : res3) {
            s.setType("娱乐");
            res.add(s);
        }
        List<AssistedChat> res_new = new ArrayList<>();

        List<Integer> indexs = util.getRandomN(num, res.size());
        for (Integer index : indexs) {
            res_new.add(res.get(index));
        }

        HashMap<String, List<AssistedChat>> res_final = new HashMap<>();
        res_final.put("topicList", res_new);

        return new R("辅助聊天内容获取成功", res_final, true);
    }
}

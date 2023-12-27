package com.huaxiaoyu.main.controller.user;

import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;


@RestController
@RequestMapping("user/register")
public class RegisterController {
    @Autowired
    private UserService userservice;

    public String[] names = {
            "土星", "木星", "天王星", "海王星", "地球", "金星", "水星", "火星", "黑猫仔仔",
            "小香猪", "米熊", "皮卡猫", "极速蜗牛", "呆萌熊宝", "精灵鼠", "铃铛羊", "棉尾兔", "可乐",
            "卡卡", "悠悠", "小新", "兜兜",
            "孤寂深海", "离殇荡情", "游于长野", "世事苍凉", "永不言变", "陪你演戏", "翩若惊鸿", "清烟无瘾",
            "惟于莽莽", "尤物佳人", "南鸢离梦", "木槿昔年", "自心难控", "岁月静好", "夏已微凉", "泪水不甜",
            "我想你笑", "久居深海", "眉眼深情", "移情别恋", "青涩懵懂", "灯火阑珊", "飞颜尘雪", "旧巷情人",
            "清欢不散", "黑色蔷薇", "笔墨书香", "清峭日暮", "盈笑凝眸", "淡若清风", "沧笙踏歌", "菁华浮梦",
            "任由漂泊", "谨言慎行", "陪你入睡", "挽袖清风", "勿念初心", "久日与至", "未曾忘你", "别来无恙",
            "伴你苦乐", "烈酒烫心"};

    @PostMapping()
    public R register(@RequestBody User user) {
        User user0 = userservice.getByName(user.getUsername());
        if (user0 != null) {
            return new R("用户名重复，注册失败", false);
        }
        String name_pre = "Huster_";
        Random r = new Random();
        Integer random = r.nextInt(23);

        BCryptPasswordEncoder s = new BCryptPasswordEncoder();
        user.setPassword(s.encode(user.getPassword()));

        String nickname = name_pre + names[random];
        user.setNickname(nickname);
        user.setCreateTime(new Date());
        Boolean flag = userservice.save(user);
        user.setPassword(null);
        HashMap<String, Boolean> res = new HashMap<>();
        res.put("isSuccess", false);
        if (flag == false) {
            return new R("注册失败", res, false);
        }

        res.put("isSuccess", true);
        return new R("注册成功", res, true);
    }


}

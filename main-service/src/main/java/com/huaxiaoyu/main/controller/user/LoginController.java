package com.huaxiaoyu.main.controller.user;

import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.service.login.LoginServcie;
import com.huaxiaoyu.main.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/login")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    LoginServcie loginServcie;

    @PostMapping()
    public R login(@RequestBody User user) {
        return loginServcie.login(user);
    }

    @RequestMapping("/logout")
    public R logout() {
        return loginServcie.logout();
    }
}

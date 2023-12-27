package com.huaxiaoyu.main.controller.user;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huaxiaoyu.main.domain.Department;
import com.huaxiaoyu.main.domain.Interest;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.service.DepartmentService;
import com.huaxiaoyu.main.service.InterestService;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${file.ip}")
    private String ip;
    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private InterestService interestService;


    @PutMapping("/updateUserInfo")
    public R changeInfo(@RequestBody User user) {
        return userService.changeInfo(user);
    }

    @GetMapping("/files/{uuid}")
    public void getFiles(@PathVariable String uuid, HttpServletResponse response) {
        OutputStream os;
        String basepath = System.getProperty("user.dir") + "/user/files/";  // 定于文件上传的根路径
        List<String> filenames = FileUtil.listFileNames(basepath);
        String fileName = filenames.stream().filter(name -> name.contains(uuid)).findAny().orElse("");
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                // attachment是以附件的形式下载，inline是浏览器打开
                response.setHeader("Content-Disposition", " attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basepath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("文件下载失败");
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/checkNameUnique/{username}")
    public R checkNameUnique(@PathVariable("username") String username) {
        Boolean flag = userService.checkNameUnique(username);
        if (flag) {
            return new R("用户名唯一", true);
        }
        return new R("用户名不唯一", false);
    }
    
    @GetMapping("/getDepartmentDict")
    public R getDepartmentDict() {
        List<Department> list = departmentService.list();
        return new R("院系", list, true);
    }

    @GetMapping("/getInterestDicts")
    public R getInterestDicts() {
        HashMap<String, List<Interest>> res = interestService.getInterestDicts();
        return new R("兴趣", res, true);
    }

    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        return userService.getUserInfo(authorization);
    }
}

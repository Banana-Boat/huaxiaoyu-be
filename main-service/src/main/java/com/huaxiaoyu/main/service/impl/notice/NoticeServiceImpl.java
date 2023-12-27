package com.huaxiaoyu.main.service.impl.notice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaxiaoyu.main.domain.LoginUser;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.notice.Notice;
import com.huaxiaoyu.main.mapper.UserMapper;
import com.huaxiaoyu.main.mapper.notice.NoticeMapper;
import com.huaxiaoyu.main.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public HashMap<String, List<HashMap<String, Object>>> get() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getId();

        HashMap<String, List<HashMap<String, Object>>> final_res = new HashMap<>();
        List<HashMap<String, Object>> res_send = new ArrayList<>();
        List<HashMap<String, Object>> res_receive = new ArrayList<>();


        LambdaQueryWrapper<Notice> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Notice::getReceiveId, userId);
        wrapper1.orderByDesc(Notice::getId);
        List<Notice> notices_receive = noticeMapper.selectList(wrapper1);

        LambdaQueryWrapper<Notice> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Notice::getSendId, userId);
        wrapper2.orderByDesc(Notice::getId);
        List<Notice> notices_send = noticeMapper.selectList(wrapper2);

        for (Notice notice : notices_receive) {
            HashMap<String, Object> res_1 = new HashMap<>();
            res_1.put("message", notice);
            User user = userMapper.selectById(notice.getSendId());
            user.setPassword(null);
            if (notice.getResult() == null || notice.getResult() != 1 || notice.getType() != 4) {
                user.setPhoneNum(null);
            }
            res_1.put("opponent", user);
            res_receive.add(res_1);
        }

        for (Notice notice : notices_send) {
            HashMap<String, Object> res_1 = new HashMap<>();
            res_1.put("message", notice);
            User user = userMapper.selectById(notice.getReceiveId());
            user.setPassword(null);
            if (notice.getResult() == null || notice.getResult() != 1 || notice.getType() != 4) {
                user.setPhoneNum(null);
            }
            res_1.put("opponent", user);
            res_send.add(res_1);
        }

        final_res.put("sendMessageList", res_send);
        final_res.put("receiveMessageList", res_receive);

        return final_res;
    }

    @Override
    public Boolean add(Integer receiveId, Integer type, Integer result) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer sendId = loginUser.getUser().getId();
        Notice notice = new Notice();
        notice.setSendId(sendId);
        notice.setReceiveId(receiveId);
        notice.setCreatedAt(new Date());
        notice.setType(type);
        notice.setResult(result);
        int insert = noticeMapper.insert(notice);
        
        if (insert == 1) {
            return true;
        }

        return false;
    }

    @Override
    public Notice getByMessageId(Integer messageId) {
        return noticeMapper.getByMessageId(messageId);
    }
}

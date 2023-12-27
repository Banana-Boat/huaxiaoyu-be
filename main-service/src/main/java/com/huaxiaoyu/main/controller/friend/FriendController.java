package com.huaxiaoyu.main.controller.friend;

import com.alibaba.fastjson.JSONObject;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.notice.Notice;
import com.huaxiaoyu.main.service.MessageService;
import com.huaxiaoyu.main.service.UserService;
import com.huaxiaoyu.main.service.friend.FriendService;
import com.huaxiaoyu.main.service.notice.NoticeService;
import com.huaxiaoyu.main.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping()
public class FriendController {
    @Autowired
    private FriendService friendService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;
    
    @GetMapping("/friend/getFriendList")
    public R get() {
        List<User> users = friendService.get();
        HashMap<String, List<User>> res = new HashMap<>();
        res.put("friendList", users);

        return new R("朋友查询成功", res, true);
    }

    @GetMapping("/message/getMessageList")
    public R getNotice() {
        HashMap<String, List<HashMap<String, Object>>> res = noticeService.get();
        return new R("消息获取成功", res, true);
    }

    @PostMapping("/friend/deleteFriend")
    public R delete(@RequestBody String jsonString) {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer receiveId = data.getInteger("opponentId");
        noticeService.add(receiveId, 0, null);

        Boolean flag2 = friendService.delete(receiveId);

        HashMap<String, Boolean> res = new HashMap<>();
        res.put("isSuccess", flag2);
        if (flag2) {
            return new R("好友删除成功！", res, true);
        }

        return new R("好友删除失败！", res, true);
    }


    @PostMapping("/friend/applyFriend")
    public R applyFriend(@RequestBody String jsonString) {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer receiveId = data.getInteger("opponentId");
        Boolean flag = noticeService.add(receiveId, 1, null);

        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        res.put("isSuccess", flag);
        if (flag) {
            return new R("好友申请成功！", res, true);
        }

        return new R("好友申请失败！", res, true);
    }

    @PostMapping("/message/replyFriend")
    public R replyFriend(@RequestBody String jsonString) {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer messageId = data.getInteger("messageId");
        Integer opponentId = data.getInteger("opponentId");
        Integer result = data.getInteger("result");
        Notice notice = noticeService.getById(messageId);
        notice.setStatus(1);
        boolean flag1 = noticeService.updateById(notice);

        Boolean flag = noticeService.add(opponentId, 2, result);

        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        res.put("isSuccess", flag);

        if (result == 1) {
            Boolean flag2 = friendService.add(opponentId);
        }
        if (flag) {
            return new R("好友申请回复成功！", res, true);
        }

        return new R("好友申请回复失败！", res, true);
    }

    @PostMapping("/friend/applyPhoneNum")
    public R applyPhoneNum(@RequestBody String jsonString) {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer opponentId = data.getInteger("opponentId");
        Boolean flag = noticeService.add(opponentId, 3, null);
        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        res.put("isSuccess", flag);
        if (flag) {
            return new R("申请获取联系方式成功！", res, true);
        }

        return new R("申请获取联系方式失败！", res, true);
    }

    @PostMapping("/message/replyPhoneNum")
    public R replyPhoneNum(@RequestBody String jsonString) {

        JSONObject data = JSONObject.parseObject(jsonString);
        Integer messageId = data.getInteger("messageId");
        Integer opponentId = data.getInteger("opponentId");
        Integer result = data.getInteger("result");
        Notice notice = noticeService.getById(messageId);
        notice.setStatus(1);
        boolean flag1 = noticeService.updateById(notice);

        Boolean flag = noticeService.add(opponentId, 4, result);

        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        res.put("isSuccess", flag);
        if (flag) {
            return new R("回复申请获取联系方式成功！", res, true);
        }
        return new R("回复申请获取联系方式失败！", res, true);
    }

    @PostMapping("/message/updateStatusOfMsg")
    public R updateStatusOfDeleteMsg(@RequestBody String jsonString) {
        JSONObject data = JSONObject.parseObject(jsonString);
        Integer messageId = data.getInteger("messageId");
        Notice notice = noticeService.getById(messageId);
        notice.setStatus(1);
        boolean flag = noticeService.updateById(notice);

        HashMap<String, Boolean> res = new HashMap<String, Boolean>();
        res.put("isSuccess", flag);
        if (flag) {
            return new R("修改好友消息的已读状态成功！", res, true);
        }
        return new R("修改好友消息的已读状态失败！", res, true);
    }


    @GetMapping("/record/getRecordList")
    public R getRecordList() {
        HashMap<String, List<HashMap<String, Object>>> res = new HashMap<>();
        List<HashMap<String, Object>> res1 = messageService.get_records();
        res.put("recordList", res1);

        return new R("聊天记录列表获取成功", res, true);
    }
}

package com.huaxiaoyu.main.consumer;

import com.alibaba.fastjson.JSONObject;
import com.huaxiaoyu.main.domain.Message;
import com.huaxiaoyu.main.domain.User;
import com.huaxiaoyu.main.domain.friend.Friend;
import com.huaxiaoyu.main.service.impl.MessageServiceImpl;
import com.huaxiaoyu.main.service.impl.UserServiceImpl;
import com.huaxiaoyu.main.service.impl.friend.FriendServiceImpl;
import com.huaxiaoyu.main.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userId}")
@Component

public class Consumer {
    final public static ConcurrentHashMap<Integer, Consumer> users = new ConcurrentHashMap<>(); //用来存放每个客户端对应的MyWebSocket对象
    public static RestTemplate restTemplate;
    public static FriendServiceImpl friendServiceimpl;
    public static RedisCache redisCache;
    @Value("${matching-server.baseUrl}")
    private static String matchingServerBaseUrl;
    private static MessageServiceImpl messageService;
    private static UserServiceImpl userService;
    private Session session; // 与某个客户端的连接会话，需要通过其来给客户端发送数据
    private User user;

    public static void startChat(Integer aId, Integer bId) {
        System.out.println("开始发送心跳包！");
        User userA = userService.getById(aId);
        User userB = userService.getById(bId);

        JSONObject respA = new JSONObject();
        respA.put("event", "start-chat");
        respA.put("opponent_username", userB.getUsername());
        respA.put("opponent_userid", userB.getId());
        respA.put("opponent_sex", userB.getSex());
        respA.put("opponent_departmentCode", userB.getDepartmentCode());

        if (users.get(userA.getId()) != null)
            users.get(userA.getId()).sendMessage("hello1");

        JSONObject respB = new JSONObject();
        respB.put("event", "start-chat");
        respB.put("opponent_username", userA.getUsername());
        respB.put("opponent_userid", userA.getId());
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        Integer id = userId;
        this.user = userService.getById(id);
        users.put(id, this);

        String msg = "websocket connect success!";
        JSONObject resp = new JSONObject();
        resp.put("event", "");
        resp.put("data", msg);
        resp.put("flag", true);
        this.session.getAsyncRemote().sendText(resp.toJSONString());
    }

    @OnClose
    public void onClose() {
        users.remove(this.user.getId());
        stopMatching();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject data = JSONObject.parseObject(message);
        System.out.println(data.get("event"));
        String event = data.getString("event");

        if ("start-matching".equals(event)) {
            startMatching();
        } else if ("stop-matching".equals(event)) {
            stopMatching();
            this.session.getAsyncRemote().sendText("stop matching success");
        }

        String sendId = this.user.getId().toString(); // 对方id

        if (redisCache.getCacheObject(sendId) != null) {
            String bId = redisCache.getCacheObject(sendId);
            User userB = userService.getById(bId);
            Boolean isFriend = friendServiceimpl.isFriend(this.user.getId(), userB.getId());

            JSONObject resp = new JSONObject();
            resp.put("event", "start-chat");
            JSONObject resp_data = new JSONObject();
            JSONObject opponent = new JSONObject();
            opponent.put("nickname", userB.getNickname());
            opponent.put("id", userB.getId());
            opponent.put("sex", userB.getSex());
            opponent.put("departmentCode", userB.getDepartmentCode());
            opponent.put("interestCodeList", userB.getInterestCodeList());
            opponent.put("headPhoto", userB.getHeadPhoto());
            resp_data.put("opponent", opponent);
            resp_data.put("isFriend", isFriend);
            resp.put("data", resp_data);
            resp.put("flag", true);
            this.session.getAsyncRemote().sendText(resp.toJSONString());
            redisCache.deleteObject(sendId);
        }


        if ("message".equals(event) || "friend-apply".equals(event) || "friend-reply".equals(event)) {
            System.out.println(data.get("data"));
            JSONObject data1 = (JSONObject) data.get("data");
            Integer receiveId = data1.getInteger("receiveId");
            Integer sendID = data1.getInteger("sendId");

            if ("message".equals(event)) {
                Message message1 = new Message();
                message1.setSendId(sendID);
                message1.setReceiveId(receiveId);
                message1.setSendText(message);
                message1.setCreateTime(new Date());
                messageService.save(message1);

                users.get(receiveId).session.getAsyncRemote().sendText(message);
            }

            if ("friend-apply".equals(event)) {
                if (friendServiceimpl.isFriend(sendID, receiveId)) {
                    JSONObject resp = new JSONObject();
                    resp.put("event", "error");
                    resp.put("flag", true);
                    JSONObject resp_data = new JSONObject();
                    resp_data.put("msg", "对方已经是您的好友，请勿重复添加！");
                    resp.put("data", resp_data);
                    this.session.getAsyncRemote().sendText(resp.toJSONString());
                } else {
                    users.get(receiveId).session.getAsyncRemote().sendText(message);
                }

            }

            if ("friend-reply".equals(event)) {
                Integer result = data1.getInteger("result");
                if (result == 1) {
                    Friend f = new Friend();
                    f.setSendId(sendID);
                    f.setReceiveId(receiveId);
                    f.setStatus(1);
                    f.setCreateTime(new Date());
                    friendServiceimpl.save(f);
                }
                users.get(receiveId).session.getAsyncRemote().sendText(message);
            }
        }


    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error!");
        error.printStackTrace();
    }

    private void startMatching() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("sex", this.user.getSex());
        String s = restTemplate.postForObject(matchingServerBaseUrl + "/match/user/add/", data, String.class);
    }

    private void stopMatching() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        String s = restTemplate.postForObject(matchingServerBaseUrl + "/match/user/remove/", data, String.class);
    }

    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        Consumer.redisCache = redisCache;
    }

    @Autowired
    public void setMessageService(MessageServiceImpl messageService) {
        Consumer.messageService = messageService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        Consumer.userService = userService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    @Autowired
    public void setFriendsService(FriendServiceImpl friendServiceimpl) {
        Consumer.friendServiceimpl = friendServiceimpl;
    }
}


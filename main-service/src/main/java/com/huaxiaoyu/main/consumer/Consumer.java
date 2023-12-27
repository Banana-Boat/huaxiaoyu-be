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
    //用来存放每个客户端对应的MyWebSocket对象。
    final public static ConcurrentHashMap<Integer, Consumer> users = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private User user;

//    private static HuaxiaoyuClient huaxiaoyuClient;

    private static MessageServiceImpl messageService;

    private static UserServiceImpl userService;

    public static RestTemplate restTemplate;

    public static FriendServiceImpl friendServiceimpl;

    private final static String addUserUrl = "http://127.0.0.1:9093/match/user/add/";
    private final static String removeUserUrl = "http://127.0.0.1:9093/match/user/remove/";

    public static RedisCache redisCache;

//    @Autowired
//    private void setHuaxiaoyuClient(HuaxiaoyuClient huaxiaoyuClient) {
//        Consumer.huaxiaoyuClient = huaxiaoyuClient;
//    }

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


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        Integer id = userId;
        System.out.println(id);

        this.user = userService.getById(id);
        System.out.println(this.user.toString());

        users.put(id, this);

        System.out.println("new connection,total:" + users.size());


        String msg = "websocket connect success!";
        JSONObject resp = new JSONObject();
        resp.put("event", "");
        resp.put("data", msg);
        resp.put("flag", true);
//        this.session.getAsyncRemote().sendText("websocket connect success!");
        this.session.getAsyncRemote().sendText(resp.toJSONString());

    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        users.remove(this.user.getId());
        stopMatching();
        System.out.println("1 close！now nums:" + users.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("new info::" + message);
        JSONObject data = JSONObject.parseObject(message);

        System.out.println(data.get("event"));
        String event = data.getString("event");
//        String sendId=data.getInteger("sendId").toString();

        if ("start-matching".equals(event)) {
            startMatching();
        } else if ("stop-matching".equals(event)) {
            stopMatching();
            this.session.getAsyncRemote().sendText("stop matching success");
        }

        //对方id获取
        String sendId = this.user.getId().toString();

        // 开始聊天
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
            //保存朋友记录
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

//            this.session.getAsyncRemote().sendText(message);
        }


    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error!");
        error.printStackTrace();
    }

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


        System.out.println(users.get(userA.getId()));

//        users.get(userA.getId()).getAsyncRemote().sendText(respA.toJSONString());
        System.out.println(userA.getId());
//        users.get(userA.getId()).getBasicRemote().sendText("hello");

        if (users.get(userA.getId()) != null) {
            users.get(userA.getId()).sendMessage("hello1");
        }

//        users.get(userA.getId()).session.getAsyncRemote().sendText(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-chat");
        respB.put("opponent_username", userA.getUsername());
        respB.put("opponent_userid", userA.getId());
        System.out.println(users.get(userB.getId()));
//        users.get(userB.getId()).getAsyncRemote().sendText(respB.toJSONString());
//        users.get(userB.getId()).session.getAsyncRemote().sendText(respB.toJSONString());
    }

    private void startMatching() {
//        System.out.println("start matching!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("sex", this.user.getSex());
        String s = restTemplate.postForObject(addUserUrl, data, String.class);
//        String s = huaxiaoyuClient.addUser(data);
        System.out.println(s);
    }

    private void stopMatching() {
        System.out.println("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        String s = restTemplate.postForObject(removeUserUrl, data, String.class);
//        String s = huaxiaoyuClient.removeUser(data);
        System.out.println(s);
    }


    //单发
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


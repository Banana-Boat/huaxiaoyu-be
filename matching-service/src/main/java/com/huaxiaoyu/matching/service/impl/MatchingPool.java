package com.huaxiaoyu.matching.service.impl;

import com.huaxiaoyu.matching.client.CrushingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();

    // 线程锁
    private final ReentrantLock lock = new ReentrantLock();

    //微服务，远程调用
    private static RestTemplate restTemplate;

    private static CrushingClient crushingClient;

    private final static String startChatUrl = "http://127.0.0.1:9092/user/startchating";

    @Autowired
    private void setCrushingClient(CrushingClient crushingClient) {
        MatchingPool.crushingClient = crushingClient;
    }

    //避免RestTemplate对象空
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, String sex) {
        lock.lock();
        try {
            System.out.println("当前匹配池人数" + players.size());
            Boolean flag = true;
            for (Player p : players) {
                if (p.getUserId() == userId) {
                    flag = false;
                }
            }
            if (flag == true) {
                players.add(new Player(userId, sex, 0));
            }

        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {  // 将所有当前玩家的等待时间加1
        for (Player player : players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatched(Player a, Player b) {  // 判断两名玩家是否匹配
//        int ratingDelta = Math.abs(a.getRating() - b.getRating());
//        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
//        if(!Objects.equals(a.getUserId(), b.getUserId()) &&!a.getSex().equals(b.getSex())){
//            return true;
//        }
//        return false;
        return true;
    }

    private void sendResult(Player a, Player b) {  // 返回匹配结果
        System.out.println("send result: " + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
//        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_id", b.getUserId().toString());
//        data.add("b_bot_id", b.getBotId().toString());
        System.out.println("matching 2!");
//        restTemplate.postForObject(startChatUrl , data , String.class);
        crushingClient.startchat(data);

    }

    private void matchPlayers() {  // 尝试匹配所有玩家
        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            if (used[i]) continue;
            for (int j = i + 1; j < players.size(); j++) {
                if (used[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if (checkMatched(a, b)) {
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!used[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();

                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

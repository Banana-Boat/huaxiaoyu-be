package com.huaxiaoyu.matching.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool implements Runnable {
    private static final ConcurrentHashMap<Integer, Player> playersMap = new ConcurrentHashMap<>(); // 线程安全的Map，用于快速查找
    private static final CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<>(); // 线程安全的List，用于快速遍历
    private static final ReentrantLock lock = new ReentrantLock(); // 可重入锁
    @Value("${main-server.baseUrl}")
    private static String mainServerBaseUrl;
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        int numThreads = 5;
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            MatchingPool matchingPool = new MatchingPool();
            RestTemplate restTemplate = new RestTemplate(); // 每个线程都创建一个RestTemplate
            matchingPool.setRestTemplate(restTemplate);

            threadPool.submit(matchingPool);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                matchPlayers();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, String sex) {
        System.out.println("当前匹配池人数" + players.size());
        lock.lock(); // 获取锁

        try {
            Player player = new Player(userId, sex);
            if (playersMap.putIfAbsent(userId, player) == null)
                players.add(player);
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock(); // 获取锁

        try {
            Player player = playersMap.remove(userId);
            if (player != null)
                players.remove(player);
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    private void sendResult(Player a, Player b) {
        System.out.println("send result: " + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        restTemplate.postForObject(mainServerBaseUrl + "/user/start-chatting", data, String.class);
    }

    private void matchPlayers() {
        lock.lock(); // 获取锁

        try {
            boolean[] used = new boolean[players.size()];

            for (int i = 0; i < players.size(); i++) {
                if (used[i]) continue;

                for (int j = i + 1; j < players.size(); j++) {
                    if (used[j]) continue;
                    Player a = players.get(i), b = players.get(j);
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }

            CopyOnWriteArrayList<Player> newPlayers = new CopyOnWriteArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                if (!used[i]) {
                    newPlayers.add(players.get(i));
                }
            }

            players.clear();
            players.addAll(newPlayers);
        } finally {
            lock.unlock(); // 释放锁
        }
    }
}

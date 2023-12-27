package com.huaxiaoyu.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huaxiaoyu.main.domain.Message;

import java.util.HashMap;
import java.util.List;

public interface MessageService extends IService<Message> {
    public List<HashMap<String, Object>> get_records();
}

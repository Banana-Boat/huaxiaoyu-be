package com.huaxiaoyu.main.service.notice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huaxiaoyu.main.domain.notice.Notice;

import java.util.HashMap;
import java.util.List;

public interface NoticeService extends IService<Notice> {
    public HashMap<String, List<HashMap<String, Object>>> get();
    
    public Boolean add(Integer receiveId, Integer type, Integer result);

    public Notice getByMessageId(Integer messageId);
}

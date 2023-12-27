package com.huaxiaoyu.main.domain.notice;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    @TableId("messageId")
    private Integer id;

    private Integer type;

    private Integer result;

    private Integer sendId;

    private Integer receiveId;

    private Integer status;

    private Date createdAt;

    private Date changeTime;
}

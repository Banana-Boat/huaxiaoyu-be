package com.huaxiaoyu.main.domain.friend;

import lombok.Data;

import java.util.Date;

@Data
public class Friend {
    private Integer id;

    private Integer sendId;

    private Integer receiveId;

    private Integer status;

    private Date createTime;

    private Date changeTime;
}

package com.huaxiaoyu.main.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;

    private Integer sendId;

    private Integer receiveId;

    private String sendText;

    private Date createTime;

    private Integer status;
    
    private Integer TextType;
}

package com.huaxiaoyu.main.domain.chat;

import lombok.Data;

@Data
public class AssistedChat {
    private Integer id;

    private String title;

    private String type;

    private String content;

    private String optionList;
}

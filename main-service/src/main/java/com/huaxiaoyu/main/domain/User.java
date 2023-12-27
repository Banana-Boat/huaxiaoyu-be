package com.huaxiaoyu.main.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private String departmentCode;

    private Integer age;

    private String phoneNum;

    private String sex;

    private Date createTime;

    private Date changeTime;

    private String headPhoto;
    
    private String interestCodeList;
}

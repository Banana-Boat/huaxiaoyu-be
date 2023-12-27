package com.huaxiaoyu.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huaxiaoyu.main.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{username}")
    User getByName(String username);
}

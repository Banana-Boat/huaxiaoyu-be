package com.huaxiaoyu.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huaxiaoyu.main.domain.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}

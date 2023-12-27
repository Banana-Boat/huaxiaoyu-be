package com.huaxiaoyu.main.mapper.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huaxiaoyu.main.domain.notice.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select * from Notice where messageId=#{messageId}")
    public Notice getByMessageId(Integer messageId);
}

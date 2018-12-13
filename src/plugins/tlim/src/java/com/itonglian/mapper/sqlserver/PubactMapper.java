package com.itonglian.mapper.sqlserver;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface PubactMapper {

    @Insert("INSERT INTO ofpubact (title,content,user_id,ts,session_id) values(#{title},#{content},#{user_id},#{ts},#{session_id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertPubact(@Param(value = "id_") String id_,
                      @Param(value = "title") String title,
                      @Param(value = "content") String content,
                      @Param(value = "user_id") String user_id,
                      @Param(value = "ts") String ts,
                      @Param(value = "session_id") String session_id);
}

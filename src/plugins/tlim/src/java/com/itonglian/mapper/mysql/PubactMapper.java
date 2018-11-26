package com.itonglian.mapper.mysql;

import com.itonglian.entity.OfPubact;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PubactMapper {

    @Insert("INSERT INTO ofpubact (id_,title,content,user_id,ts,session_id) values(#{id_},#{title},#{content},#{user_id},#{ts},#{session_id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertPubact(@Param(value = "id_") String id_,
                      @Param(value = "title") String title,
                      @Param(value = "content") String content,
                      @Param(value = "user_id") String user_id,
                      @Param(value = "ts") String ts,
                      @Param(value = "session_id") String session_id);

    @Select("SELECT * FROM ofpubact WHERE session_id = #{session_id}")
    @Options(useCache = true)
    List<OfPubact> findBySession(@Param(value = "session_id") String session_id);

    @Update("UPDATE ofpubact SET title = #{title},content = #{content} WHERE id_ = #{id_}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void update(@Param(value = "id_") long id_,
                @Param(value = "title") String title,
                @Param(value = "content") String content);

    @Delete("DELETE FROM ofpubact WHERE id_ = #{id_}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void delete(@Param(value = "id_") long id_);
}

package com.itonglian.mapper;

import com.itonglian.entity.OfPubact;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PubactMapper {

    @Insert("INSERT INTO ofpubact (title,content,user_id,ts,session_id) values(#{title},#{content},#{user_id},#{ts},#{session_id})")
    @Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_")
    void insertPubact(@Param(value = "title") String title,
                      @Param(value = "content") String content,
                      @Param(value = "user_id") String user_id,
                      @Param(value = "ts") String ts,
                      @Param(value = "session_id") String session_id);

    @Select("SELECT * FROM ofpubact WHERE session_id = #{session_id}")
    List<OfPubact> findBySession(@Param(value = "session_id") String session_id);

    @Update("UPDATE ofpubact SET title = #{title},content = #{content} WHERE id_ = #{id_}")
    void update(@Param(value = "id_") long id_,
                @Param(value = "title") String title,
                @Param(value = "content") String content);

    @Delete("DELETE FROM ofpubact WHERE id_ = #{id_}")
    void delete(@Param(value = "id_") long id_);
}

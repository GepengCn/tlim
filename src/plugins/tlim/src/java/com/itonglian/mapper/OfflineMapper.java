package com.itonglian.mapper;

import com.itonglian.entity.OfCustomOffline;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OfflineMapper {

    @Insert("INSERT INTO ofcustomoffline (msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id,msg_status) VALUES(#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id},#{msg_status})")
    @Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_",flushCache = Options.FlushCachePolicy.TRUE)
    void insertOffline(OfCustomOffline ofCustomOffline);

    @Update("UPDATE ofcustomoffline SET msg_status = #{msg_status} WHERE msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void updateStatus(@Param(value = "msg_status") int msg_status,
                      @Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByMsgId(@Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE msg_to = #{msg_to}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByUser(@Param(value = "msg_to") String msg_to);

    @Select("SELECT * FROM ofcustomoffline WHERE msg_to = #{msg_to}")
    @Options(useCache = true)
    List<OfCustomOffline> findByUser(@Param(value = "msg_to") String msg_to);

    @Select("SELECT * FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    @Options(useCache = true)
    List<OfCustomOffline> findByMsgId(@Param(value = "msg_id") String msg_id);

    @Select("SELECT * FROM ofcustomoffline")
    @Options(useCache = true)
    List<OfCustomOffline> findAll();

    @Delete("DELETE FROM ofcustomoffline WHERE session_id = #{session_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteBySession(@Param(value = "session_id") String session_id);
}

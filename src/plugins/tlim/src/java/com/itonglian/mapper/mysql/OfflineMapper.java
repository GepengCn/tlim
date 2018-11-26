package com.itonglian.mapper.mysql;

import com.itonglian.entity.OfCustomOffline;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OfflineMapper {

    @Insert("INSERT INTO ofcustomoffline (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id,msg_status,delete_user) VALUES(#{id_},#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id},#{msg_status},#{delete_user})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertOffline(OfCustomOffline ofCustomOffline);

    @Update("UPDATE ofcustomoffline SET msg_status = #{msg_status} WHERE msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void updateStatus(@Param(value = "msg_status") int msg_status,
                      @Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByMsgId(@Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE delete_user = #{delete_user}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByUser(@Param(value = "delete_user") String delete_user);

    @Select("SELECT * FROM ofcustomoffline WHERE delete_user = #{delete_user}")
    @Options(useCache = true)
    List<OfCustomOffline> findByUser(@Param(value = "delete_user") String delete_user);

    @Select("SELECT * FROM ofcustomoffline WHERE delete_user = #{delete_user} AND msg_time >=#{msg_time}")
    @Options(useCache = true)
    List<OfCustomOffline> findByUserAfterThatTime(@Param(value = "delete_user") String delete_user,
                                                  @Param(value = "msg_time") String msg_time);

    @Select("SELECT * FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    @Options(useCache = true)
    List<OfCustomOffline> findByMsgId(@Param(value = "msg_id") String msg_id);

    @Select("SELECT * FROM ofcustomoffline")
    @Options(useCache = true)
    List<OfCustomOffline> findAll();

    @Delete("DELETE FROM ofcustomoffline WHERE session_id = #{session_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteBySession(@Param(value = "session_id") String session_id);

    @Delete("DELETE FROM ofcustomoffline WHERE delete_user = #{delete_user} AND msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByUserAndId(@Param(value = "delete_user") String delete_user,
                           @Param(value = "msg_id") String msg_id);
}

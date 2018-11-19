package com.itonglian.mapper;

import com.itonglian.entity.OfCustomOffline;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OfflineMapper {

    @Insert("INSERT INTO ofcustomoffline (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id,msg_status) VALUES(#{id_},#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id},#{msg_status})")
    void insertOffline(OfCustomOffline ofCustomOffline);

    @Update("UPDATE ofcustomoffline SET msg_status = #{msg_status} WHERE msg_id = #{msg_id}")
    void updateStatus(@Param(value = "msg_status") int msg_status,
                      @Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    void deleteByMsgId(@Param(value = "msg_id") String msg_id);

    @Delete("DELETE FROM ofcustomoffline WHERE msg_to = #{msg_to}")
    void deleteByUser(@Param(value = "msg_to") String msg_to);

    @Select("SELECT * FROM ofcustomoffline WHERE msg_to = #{msg_to}")
    List<OfCustomOffline> findByUser(@Param(value = "msg_to") String msg_to);

    @Select("SELECT * FROM ofcustomoffline WHERE msg_id = #{msg_id}")
    List<OfCustomOffline> findByMsgId(@Param(value = "msg_id") String msg_id);

    @Select("SELECT * FROM ofcustomoffline")
    List<OfCustomOffline> findAll();
}

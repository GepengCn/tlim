package com.itonglian.mapper;

import com.itonglian.bean.MessageRead;
import com.itonglian.bean.SessionRead;
import com.itonglian.entity.OfStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StatusMapper {

    @Insert("INSERT INTO ofstatus (msg_id,sender,reader,status) values(#{msg_id},#{sender},#{reader},#{status})")
    @Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_",flushCache = Options.FlushCachePolicy.TRUE)
    void insertStatus(OfStatus ofStatus);

    @Update("UPDATE ofstatus SET status=#{status} WHERE msg_id=#{msg_id} AND reader = #{reader}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void update(@Param(value = "status") int status,
                @Param(value = "msg_id") String msg_id,
                @Param(value = "reader") String reader);

    @Delete("DELETE FROM ofstatus WHERE msg_id=#{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void delete(@Param(value = "msg_id") String msg_id);

    @Select("SELECT COUNT(1) FROM ofstatus WHERE msg_id=#{msg_id} AND reader = #{reader}")
    @Options(useCache = true)
    int isExist(@Param(value = "msg_id") String msg_id,
                @Param(value = "reader") String reader);

    @Select("select msg_id,count(1) as readNum FROM ofstatus WHERE msg_id IN (SELECT t.msg_id" +
            " FROM (select * from ofmessage" +
            " WHERE session_id = #{session_id}" +
            " ORDER BY msg_time desc limit #{start},#{length}) t" +
            " ) AND sender = #{sender} group by msg_id")
    @Options(useCache = true)
    List<MessageRead> findSessionRead(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length,
                                      @Param(value = "sender") String sender);
    @Select("select msg_id,count(1) as readNum FROM ofstatus WHERE msg_id IN (SELECT t.msg_id" +
            " FROM (select * from ofmessage" +
            " WHERE msg_from = #{msg_from} AND msg_to = #{msg_to}" +
            " ORDER BY msg_time desc limit #{start},#{length}) t" +
            " ) AND sender = #{msg_from} group by msg_id")
    @Options(useCache = true)
    List<MessageRead> findChatRead(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);

}

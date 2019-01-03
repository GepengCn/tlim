package com.itonglian.mapper.sqlserver;

import com.itonglian.bean.MessageRead;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatusMapper {

    @Select("select msg_id,count(1) as readNum FROM ofstatus WHERE msg_id IN (SELECT t.msg_id" +
            " FROM (select * from (select *,ROW_NUMBER() OVER (Order by id_) AS RowId from ofmessage" +
            " WHERE session_id = #{session_id}" +
            " ) p where p.RowId between #{start} AND #{length}) t" +
            " ) AND sender = #{sender} group by msg_id")
    @Options(useCache = true)
    List<MessageRead> findSessionRead(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length,
                                      @Param(value = "sender") String sender);
    @Select("select msg_id,count(1) as readNum FROM ofstatus WHERE msg_id IN (SELECT t.msg_id" +
            " FROM (select * from (select * from ofmessage" +
            " WHERE msg_from = #{msg_from} AND msg_to = #{msg_to}" +
            " ) p where p.RowId between #{start} AND #{length}) t" +
            " ) AND sender = #{msg_from} group by msg_id")
    @Options(useCache = true)
    List<MessageRead> findChatRead(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);
}

package com.itonglian.mapper.oracle;

import com.itonglian.bean.MessageRead;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatusMapper {

    @Select("select msg_id,count(1) as readNum FROM ofstatus WHERE msg_id IN (SELECT msg_id" +
            " FROM (SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE session_id = #{session_id}  AND rownum<=#{length} ORDER BY msg_time desc) WHERE rn>=#{start})) AND sender = #{sender} group by msg_id")
    @Options(useCache = true)
    List<MessageRead> findSessionRead(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length,
                                      @Param(value = "sender") String sender);

    @Select("SELECT msg_id,count(1) AS readNum FROM ofstatus WHERE msg_id IN (" +
            "SELECT msg_id FROM (" +
            "SELECT*FROM (" +
            "SELECT ofmessage.*,ROWNUM AS rn FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to=#{msg_to} AND ROWNUM<=#{length}) t WHERE rn>=#{start} ORDER BY msg_time DESC) WHERE sender=#{msg_from}) GROUP BY msg_id")
    @Options(useCache = true)
    List<MessageRead> findChatRead(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);

}

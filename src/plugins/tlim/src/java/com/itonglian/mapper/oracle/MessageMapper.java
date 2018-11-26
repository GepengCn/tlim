package com.itonglian.mapper.oracle;

import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper{


    @Select("SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE session_id = #{session_id}  AND rownum<=#{length} ORDER BY msg_time desc) WHERE rn>=#{start}")
    @Options(useCache = true)
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type} AND msg_type !='MTT-100' UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from} AND msg_type LIKE #{msg_type}  AND rownum<=#{length} ORDER BY msg_time desc) WHERE rn>=#{start}")
    @Options(useCache = true)
    List<OfMessage> findPageByChat(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="msg_type") String msg_type,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);


}

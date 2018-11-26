package com.itonglian.mapper.oracle;

import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper{


    @Insert("INSERT INTO ofmessage (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(#{id_},#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id})")
    //@Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_",flushCache = Options.FlushCachePolicy.TRUE)
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectKey(statement="select S_ofmessage.NEXTVAL as ID_ from dual", keyProperty="ID_", before=true, resultType=int.class)
    void insertMessage(OfMessage ofMessage);

    @Select("SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE session_id = #{session_id}  AND rownum<=#{length} ORDER BY msg_time desc) WHERE rn>=#{start}")
    @Options(useCache = true)
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT count(*) AS total FROM ofmessage WHERE msg_id=#{msg_id}")
    @Options(useCache = true)
    int isExist(@Param(value="msg_id") String msg_id);


    @Select("SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type} AND msg_type !='MTT-100' UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from} AND msg_type LIKE #{msg_type}  AND rownum<=#{length} ORDER BY msg_time desc) WHERE rn>=#{start}")
    @Options(useCache = true)
    List<OfMessage> findPageByChat(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="msg_type") String msg_type,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);


}

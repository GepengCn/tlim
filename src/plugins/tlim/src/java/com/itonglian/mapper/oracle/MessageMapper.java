package com.itonglian.mapper.oracle;

import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper{


    @Insert("INSERT INTO OFMESSAGE (ID_,MSG_ID,MSG_TYPE,MSG_FROM,MSG_TO,MSG_TIME,BODY,SESSION_ID) VALUES(#{id_},#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time,jdbcType=VARCHAR},#{body},#{session_id})")
    //@Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_",flushCache = Options.FlushCachePolicy.TRUE)
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    //@SelectKey(statement="select S_ofmessage.NEXTVAL as ID_ from dual", keyProperty="ID_", before=true, resultType=int.class)
    void insertMessage(OfMessage ofMessage);

    @Select("SELECT * FROM (SELECT OFMESSAGE.*,ROWNUM as rn FROM OFMESSAGE WHERE SESSION_ID = #{session_id}  AND ROWNUM<=#{length} ) WHERE rn>=#{start} ORDER BY MSG_TIME DESC")
    @Options(useCache = true)
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM OFMESSAGE WHERE MSG_ID=#{msg_id}")
    @Options(useCache = true)
    int isExist(@Param(value="msg_id") String msg_id);


    @Select("SELECT * FROM (SELECT OFMESSAGE.*,ROWNUM as rn FROM OFMESSAGE WHERE MSG_FROM=#{msg_from} AND MSG_TO = #{msg_to} AND MSG_TYPE LIKE #{msg_type}  UNION SELECT OFMESSAGE.*,ROWNUM as rn FROM OFMESSAGE WHERE MSG_FROM = #{msg_to} AND MSG_TO = #{msg_from} AND MSG_TYPE LIKE #{msg_type}  AND ROWNUM<=#{length} ) WHERE rn>=#{start} ORDER BY MSG_TIME DESC")
    @Options(useCache = true)
    List<OfMessage> findPageByChat(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="msg_type") String msg_type,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);



    @Select("SELECT * FROM (SELECT OFMESSAGE.*,ROWNUM as rn FROM OFMESSAGE WHERE MSG_TO = #{msg_to} AND MSG_TYPE = #{msg_type}  AND ROWNUM<=#{length} ) WHERE rn>=#{start} ORDER BY MSG_TIME DESC")
    @Options(useCache = true)
    List<OfMessage> findPageBySystem(@Param(value="msg_to") String msg_to,
                                     @Param(value="msg_type") String msg_type,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);


}

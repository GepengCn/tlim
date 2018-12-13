package com.itonglian.mapper.sqlserver;

import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper {

    @Insert("INSERT INTO ofmessage (msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertMessage(OfMessage ofMessage);

    @Select("SELECT count(*) AS total FROM ofmessage WHERE msg_id=#{msg_id}")
    @Options(useCache = true)
    int isExist(@Param(value="msg_id") String msg_id);

    @Select("SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID_) AS ROWID FROM OFMESSAGE WHERE SESSION_ID = #{session_id}) T  WHERE  T.ROWID BETWEEN #{start} AND #{length}  ORDER BY MSG_TIME DESC")
    @Options(useCache = true)
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("select * from (SELECT *,ROW_NUMBER() OVER (Order by id_) AS RowId FROM ofmessage WHERE msg_type = #{msg_type} AND msg_to = #{msg_to}) t where  t.RowId between #{start} and #{length}  ORDER BY msg_time desc ")
    @Options(useCache = true)
    List<OfMessage> findPageBySystem(@Param(value="msg_type") String msg_type,
                                     @Param(value="msg_to") String msg_to,
                                     @Param(value="start") int start,
                                     @Param(value="length") int length);
}

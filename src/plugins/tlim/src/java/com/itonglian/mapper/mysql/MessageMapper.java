package com.itonglian.mapper.mysql;

import com.itonglian.entity.Message;
import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper{

    @Insert("INSERT INTO ofmessage (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(#{id_},#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertMessage(OfMessage ofMessage);


    @Select("SELECT count(*) AS total FROM ofmessage WHERE msg_id=#{msg_id}")
    @Options(useCache = true)
    int isExist(@Param(value="msg_id") String msg_id);

    @Select("SELECT * FROM ofmessage WHERE session_id = #{session_id}  ORDER BY msg_time desc limit #{start},#{length}")
    @Options(useCache = true)
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM ofmessage WHERE session_id = #{session_id} ")
    @Options(useCache = true)
    int findPageTotalBySession(@Param(value="session_id") String session_id);

    @Select("SELECT * FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type}  UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from} AND msg_type LIKE #{msg_type}  ORDER BY msg_time desc limit #{start},#{length}")
    @Options(useCache = true)
    List<OfMessage> findPageByChat(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="msg_type") String msg_type,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM (SELECT * FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type}  UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from}  AND msg_type LIKE #{msg_type}  ) t  ")
    @Options(useCache = true)
    int findPageTotalByChat(@Param(value="msg_from") String msg_from,
                            @Param(value="msg_to") String msg_to,
                            @Param(value="msg_type") String msg_type);


    @Delete("DELETE FROM ofmessage WHERE session_id = #{msg_from} AND msg_from = #{msg_from}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteByUserAndSession(@Param(value="session_id") String session_id,
                                @Param(value="msg_from") String msg_from);

    @Delete("DELETE FROM ofmessage WHERE session_id = #{session_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteBySession(@Param(value="session_id") String session_id);

    @Select("SELECT msg_time FROM ofmessage WHERE msg_id = #{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    String findMessageTime(@Param(value="msg_id") String msg_id);

    @Select("SELECT msg_id,msg_type,msg_from,msg_to,msg_time,body FROM ofmessage WHERE msg_to = #{msg_to} AND msg_time >=#{msg_time}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    List<Message> findMessageAfter(@Param(value="msg_to") String msg_to,
                                   @Param(value="msg_time") String msg_time);

    @Delete({"DELETE FROM ofmessage where msg_id=#{msg_id}"})
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void deleteById(@Param(value="msg_id") String msg_id);

    @Select("SELECT * FROM ofmessage WHERE msg_type = #{msg_type} AND msg_to = #{msg_to} ORDER BY msg_time desc limit #{start},#{length}")
    @Options(useCache = true)
    List<OfMessage> findPageBySystem(@Param(value="msg_type") String msg_type,
                                      @Param(value="msg_to") String msg_to,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM ofmessage WHERE msg_type = #{msg_type} AND msg_to = #{msg_to}")
    @Options(useCache = true)
    int findPageTotalBySystem(@Param(value="msg_type") String msg_type,
                              @Param(value="msg_to") String msg_to);


}


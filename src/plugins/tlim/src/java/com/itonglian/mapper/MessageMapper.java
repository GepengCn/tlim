package com.itonglian.mapper;

import com.itonglian.entity.Message;
import com.itonglian.entity.OfMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper {

    @Insert("INSERT INTO ofmessage (msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time},#{body},#{session_id})")
    @Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_")
    void insertMessage(OfMessage ofMessage);


    @Select("SELECT count(*) AS total FROM ofmessage WHERE msg_id=#{msg_id}")
    int isExist(@Param(value="msg_id") String msg_id);

    @Select("SELECT * FROM ofmessage WHERE session_id = #{session_id}  ORDER BY msg_time desc limit #{start},#{length}")
    List<OfMessage> findPageBySession(@Param(value="session_id") String session_id,
                                      @Param(value="start") int start,
                                      @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM ofmessage WHERE session_id = #{session_id} ")
    int findPageTotalBySession(@Param(value="session_id") String session_id);

    @Select("SELECT * FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type}  UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from} AND msg_type LIKE #{msg_type}  ORDER BY msg_time desc limit #{start},#{length}")
    List<OfMessage> findPageByChat(@Param(value="msg_from") String msg_from,
                                   @Param(value="msg_to") String msg_to,
                                   @Param(value="msg_type") String msg_type,
                                   @Param(value="start") int start,
                                   @Param(value="length") int length);

    @Select("SELECT COUNT(*) AS total FROM (SELECT * FROM ofmessage WHERE msg_from=#{msg_from} AND msg_to = #{msg_to} AND msg_type LIKE #{msg_type}  UNION SELECT * FROM ofmessage WHERE msg_from = #{msg_to} AND msg_to = #{msg_from}  AND msg_type LIKE #{msg_type}  ) t  ")
    int findPageTotalByChat(@Param(value="msg_from") String msg_from,
                            @Param(value="msg_to") String msg_to,
                            @Param(value="msg_type") String msg_type);


    @Delete("DELETE FROM ofmessage WHERE session_id = #{msg_from} AND msg_from = #{msg_from}")
    void deleteByUserAndSession(@Param(value="session_id") String session_id,
                                @Param(value="msg_from") String msg_from);

    @Delete("DELETE FROM ofmessage WHERE session_id = #{msg_from}")
    void deleteBySession(@Param(value="session_id") String session_id);

    @Select("SELECT msg_time FROM ofmessage WHERE msg_id = #{msg_id}")
    String findMessageTime(@Param(value="msg_id") String msg_id);

    @Select("SELECT msg_id,msg_type,msg_from,msg_to,msg_time,body FROM ofmessage WHERE msg_to = #{msg_to} AND msg_time >=#{msg_time}")
    List<Message> findMessageAfter(@Param(value="msg_to") String msg_to,
                                   @Param(value="msg_time") String msg_time);
}

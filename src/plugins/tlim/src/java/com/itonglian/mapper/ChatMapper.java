package com.itonglian.mapper;

import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ChatMapper {


    @Delete("DELETE FROM ofchat where msg_id=#{msg_id}")
    void deleteById(@Param(value="msg_id") String msg_id);

    @Select("SELECT chat_id,chat_name,chat_user,chat_other,chat_create_time,chat_modify_time FROM ofchat WHERE chat_other = #{chat_other}")
    List<OfChat> chatList(@Param(value="chat_other") String chat_other);

    @Select("SELECT * FROM ofchat WHERE chat_user = #{chat_user} AND chat_other = #{chat_other}")
    int isExistChat(@Param(value="chat_user")String chat_user,
                    @Param(value="chat_other")String chat_other);

    @Insert("INSERT INTO ofchat(chat_id,chat_name,chat_user,chat_other,chat_create_time,chat_modify_time,chat_pic) VALUES(#{chat_id},#{chat_name},#{chat_user},#{chat_other},#{chat_create_time},#{chat_modify_time},#{chat_pic})")
//    @Options(useGeneratedKeys=true, keyProperty="chat_id", keyColumn="chat_id")
    void insertChat(OfChat ofChat);

    @Update("UPDATE ofchat SET chat_modify_time = #{chat_modify_time} WHERE chat_user = #{chat_user} AND chat_other = #{chat_other}")
    void modify(String chat_modify_time,String chat_user,String chat_other);


}

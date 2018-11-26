package com.itonglian.mapper.mysql;

import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ChatMapper {




    @Select("SELECT chat_id,chat_name,chat_user,chat_other,chat_create_time,chat_modify_time FROM ofchat WHERE chat_other = #{chat_other}")
    @Options(useCache = true)
    List<OfChat> chatList(@Param(value="chat_other") String chat_other);

    @Select("SELECT count(1) FROM ofchat WHERE chat_user = #{chat_user} AND chat_other = #{chat_other}")
    @Options(useCache = true)
    int isExistChat(@Param(value="chat_user")String chat_user,
                    @Param(value="chat_other")String chat_other);

    @Insert("INSERT INTO ofchat(chat_id,chat_name,chat_user,chat_other,chat_create_time,chat_modify_time,chat_pic) VALUES(#{chat_id},#{chat_name},#{chat_user},#{chat_other},#{chat_create_time},#{chat_modify_time},#{chat_pic})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertChat(OfChat ofChat);

    @Update("UPDATE ofchat SET chat_modify_time = #{chat_modify_time} WHERE chat_user = #{chat_user} AND chat_other = #{chat_other}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void modify(@Param(value = "chat_modify_time") String chat_modify_time,
                @Param(value = "chat_user") String chat_user,
                @Param(value = "chat_other") String chat_other);


}

package com.itonglian.mapper;

import com.itonglian.entity.OfSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SessionMapper {

    @Insert("INSERT INTO ofsession (session_id,session_type,session_name,session_create_time,session_modify_time,session_delete_time,session_valid,session_user) VALUES(#{session_id},#{session_type},#{session_name},#{session_create_time},#{session_modify_time},#{session_delete_time},#{session_valid},#{session_user})")
    //@Options(useGeneratedKeys=true, keyProperty="session_id", keyColumn="session_id")
    void insertSession(OfSession ofSession);

    @Delete("DELETE FROM ofsession WHERE session_id = #{session_id}")
    void deleteById(@Param(value="session_id") String session_id);

    @Update("UPDATE ofsession SET session_name = #{session_name},session_modify_time = #{session_modify_time} WHERE session_id = #{session_id}")
    void rename(@Param(value="session_name") String session_name,
                @Param(value="session_modify_time") String session_modify_time,
                @Param(value="session_id") String session_id);

    @Select("SELECT * FROM ofsession WHERE session_id = #{session_id}")
    List<OfSession> findBySessionId(@Param(value="session_id") String session_id);

    @Select("SELECT A.* FROM ofsession A,ofsubscriber B WHERE A.session_id = B.session_id AND B.user_id = #{user_id} AND A.session_valid = #{session_valid} AND A.session_type <> #{session_type}")
    List<OfSession> findByUser(@Param(value = "user_id") String user_id,
                               @Param(value = "session_valid") int session_valid,
                               @Param(value = "session_type") int session_type);
    @Update("UPDATE ofsession SET session_pic=#{session_pic} WHERE session_id=#{session_id}")
    void updatePic(@Param(value = "session_pic") String session_pic,
                   @Param(value = "session_id") String session_id);

    @Update("UPDATE ofsession SET session_modify_time = #{session_modify_time} WHERE session_id = #{session_id}")
    void modify(@Param(value = "session_modify_time") String session_modify_time,
                   @Param(value = "session_id") String session_id);

    @Update("UPDATE ofsession SET session_valid = #{session_valid} WHERE session_id = #{session_id}")
    void switchSession(@Param(value = "session_valid") int session_valid,
                       @Param(value = "session_id") String session_id);
}


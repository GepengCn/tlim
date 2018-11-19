package com.itonglian.mapper;

import com.itonglian.entity.OfSubscriber;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubscriberMapper {

    @Insert("INSERT INTO ofsubscriber (user_id,user_name,acct_login,pic,session_id,ts) VALUES(#{user_id},#{user_name},#{acct_login},#{pic},#{session_id},#{ts})")
    void insertSubscriber(OfSubscriber ofSubscriber);

    @Delete("DELETE FROM ofsubscriber WHERE user_id = #{user_id} AND session_id = #{session_id}")
    void deleteBySessionAndUser(@Param(value="user_id") String user_id,
                                @Param(value="session_id") String session_id);

    @Select("SELECT * FROM ofsubscriber WHERE session_id = #{session_id}")
    List<OfSubscriber> findBySession(@Param(value="session_id") String session_id);

    @Delete("DELETE FROM ofsubscriber WHERE session_id = #{session_id}")
    void deleteBySession(@Param(value="session_id") String session_id);
}

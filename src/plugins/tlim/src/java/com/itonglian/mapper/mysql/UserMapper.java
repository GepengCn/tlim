package com.itonglian.mapper.mysql;

import com.itonglian.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT a.user_id,a.user_name,a.acct_login,b.user_email,b.pic_url,a.app_push_code FROM isc_user a,isc_user_info b  WHERE a.user_id = b.user_id AND a.dr=#{dr} AND b.dr=#{dr}")
    @Options(useCache = true)
    List<User> findAll(@Param(value = "dr") String dr);

    @Select("SELECT username as user_id,name as user_name,username as acct_login FROM ofuser")
    @Options(useCache = true)
    List<User> findLocalAll();

    @Delete("DELETE FROM ofUser WHERE username <> #{username}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void removeAll(@Param(value = "username") String username);

    @Select("SELECT app_push_code FROM isc_user WHERE user_id = #{user_id} AND dr = #{dr}")
    @Options(useCache = true)
    String findAppPushCode(@Param(value = "user_id") String user_id,
                           @Param(value = "dr") String dr);


    @Update("UPDATE isc_user set app_push_code = #{app_push_code} WHERE user_id = #{user_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void registerAppPushCode(@Param(value = "app_push_code") String app_push_code,
                             @Param(value = "user_id") String user_id);

    @Select("SELECT count(1) FROM isc_user a,isc_user_info b  WHERE a.user_id = b.user_id AND a.dr=#{dr} AND b.dr=#{dr}")
    @Options(useCache = false)
    int count(@Param(value = "dr") String dr);

    @Delete("DELETE FROM ofUser WHERE username = #{userId}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void remove(@Param(value = "userId") String userId);

}

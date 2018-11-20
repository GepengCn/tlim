package com.itonglian.mapper;

import com.itonglian.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("SELECT a.user_id,a.user_name,a.acct_login,b.user_email,b.pic_url,a.app_push_code FROM isc_user a,isc_user_info b  WHERE a.user_id = b.user_id AND a.dr=#{dr} AND b.dr=#{dr}")
    @Options(useCache = true)
    List<User> findAll(@Param(value = "dr") String dr);

    @Delete("DELETE FROM ofUser WHERE username <> #{username}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void removeAll(@Param(value = "username") String username);

    @Select("SELECT app_push_code FROM isc_user WHERE user_id = #{user_id} AND dr = #{dr}")
    @Options(useCache = true)
    String findAppPushCode(@Param(value = "user_id") String user_id,
                           @Param(value = "dr") String dr);

}

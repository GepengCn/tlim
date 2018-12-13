package com.itonglian.mapper.sqlserver;

import com.itonglian.entity.OfSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface SessionMapper {

    @Insert("INSERT INTO ofsession (session_id,session_type,session_name,session_create_time,session_modify_time,session_delete_time,session_valid,\"session_user\") VALUES(#{session_id},#{session_type},#{session_name},#{session_create_time},#{session_modify_time},#{session_delete_time},#{session_valid},#{session_user})")
    //@Options(useGeneratedKeys=true, keyProperty="session_id", keyColumn="session_id")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertSession(OfSession ofSession);
}

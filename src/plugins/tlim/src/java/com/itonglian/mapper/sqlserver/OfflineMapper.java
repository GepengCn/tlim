package com.itonglian.mapper.sqlserver;

import com.itonglian.entity.OfCustomOffline;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OfflineMapper {

    @Insert("INSERT INTO ofcustomoffline (msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id,msg_status,delete_user) VALUES(#{msg_id},#{msg_type},#{msg_from},#{msg_to},#{msg_time,jdbcType=VARCHAR},#{body},#{session_id},#{msg_status},#{delete_user})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertOffline(OfCustomOffline ofCustomOffline);
}

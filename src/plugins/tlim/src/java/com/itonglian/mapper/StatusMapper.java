package com.itonglian.mapper;

import com.itonglian.entity.OfStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StatusMapper {

    @Insert("INSERT INTO ofstatus (msg_id,reader,status) values(#{msg_id},#{reader},#{status})")
    @Options(useGeneratedKeys=true, keyProperty="id_", keyColumn="id_",flushCache = Options.FlushCachePolicy.TRUE)
    void insertStatus(OfStatus ofStatus);

    @Update("UPDATE ofstatus SET status=#{status} WHERE msg_id=#{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void update(@Param(value = "status") int status,
                @Param(value = "msg_id") String msg_id);

    @Select("SELECT * FROM ofstatus WHERE reader=#{reader}")
    @Options(useCache = true)
    List<OfStatus> findByReader(@Param(value = "reader") String reader);

    @Delete("DELETE FROM ofstatus WHERE msg_id=#{msg_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void delete(@Param(value = "msg_id") String msg_id);
}

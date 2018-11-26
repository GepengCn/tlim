package com.itonglian.mapper.mysql;

import com.itonglian.entity.OfStyle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StyleMapper {

    @Insert("INSERT INTO ofstyle (style_id,style_name,style_value,user_id) VALUES(#{style_id},#{style_name},#{style_value},#{user_id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void insertStyle(OfStyle ofStyle);

    @Select("SELECT * FROM ofstyle WHERE user_id = #{user_id}")
    @Options(useCache = true)
    List<OfStyle> findByUser(String user_id);

    @Select("SELECT COUNT(1) FROM ofstyle WHERE style_name = #{style_name} AND user_id = #{user_id}")
    @Options(useCache = true)
    int isExist(@Param(value = "style_name") String style_name,
                @Param(value = "user_id") String user_id);

    @Update("UPDATE ofstyle SET style_value = #{style_value} WHERE style_name = #{style_name} AND user_id = #{user_id}")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    void update(@Param(value = "style_value") int style_value,
                @Param(value = "style_name") String style_name,
                @Param(value = "user_id") String user_id);
}

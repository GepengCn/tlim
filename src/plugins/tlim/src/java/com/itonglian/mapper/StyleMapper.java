package com.itonglian.mapper;

import com.itonglian.entity.OfStyle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StyleMapper {

    @Insert("INSERT INTO ofstyle (style_name,style_value,user_id) VALUES(#{style_name},#{style_value},#{user_id})")
    @Options(useGeneratedKeys=true, keyProperty="style_id", keyColumn="style_id",flushCache = Options.FlushCachePolicy.TRUE)
    void insertStyle(OfStyle ofStyle);

    @Select("SELECT * FROM ofstyle WHERE user_id = #{user_id}")
    @Options(useCache = true)
    List<OfStyle> findByUser(String user_id);
}

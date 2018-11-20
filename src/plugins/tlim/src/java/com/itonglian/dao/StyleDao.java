package com.itonglian.dao;

import com.itonglian.entity.OfStyle;

import java.util.List;

public interface StyleDao {

    void add(OfStyle ofStyle);

    List<OfStyle> query(String user_id);

    boolean isExist(String style_name,String user_id);

    void update(int style_value,String style_name,String user_id);


}

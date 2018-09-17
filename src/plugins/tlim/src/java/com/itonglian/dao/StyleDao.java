package com.itonglian.dao;

import com.itonglian.entity.OfStyle;

import java.util.List;

public interface StyleDao {

    public void add(OfStyle ofStyle);

    public List<OfStyle> query(String user_id);
}

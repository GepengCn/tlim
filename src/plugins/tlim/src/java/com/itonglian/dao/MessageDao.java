package com.itonglian.dao;

import com.itonglian.entity.OfMessage;

import java.util.List;

public interface MessageDao {

    public List<OfMessage> findHistory(String session_id,String user_id, int start, int length);
}

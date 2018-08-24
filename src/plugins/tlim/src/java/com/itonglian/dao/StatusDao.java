package com.itonglian.dao;

import com.itonglian.bean.SessionUnread;
import com.itonglian.entity.OfStatus;

import java.util.List;

public interface StatusDao {

    public void add(OfStatus ofStatus);

    public void update(String session_id);

    public List<SessionUnread> findUnread(String msg_to);

}

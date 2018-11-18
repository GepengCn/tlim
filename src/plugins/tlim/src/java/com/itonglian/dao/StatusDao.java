package com.itonglian.dao;

import com.itonglian.bean.SessionRead;
import com.itonglian.bean.SessionUnread;
import com.itonglian.entity.OfStatus;

import java.util.List;

public interface StatusDao {

    public void add(OfStatus ofStatus);

    public void update(String msg_id,int status);

    public List<OfStatus> findByReader(String reader);

    public void delete(String msg_id);


}

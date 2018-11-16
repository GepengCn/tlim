package com.itonglian.dao;

import com.itonglian.entity.OfCustomOffline;
import com.itonglian.entity.OfMessage;

import java.util.List;

public interface OfflineDao {

    public void add(OfMessage ofMessage);

    public void updateAfterAdd(OfMessage ofMessage,int msg_status);

    public void updateStatus(String msg_id,int msg_status);

    public void delete(String msg_id);

    public void deleteByUser(String user_id);

    public List<OfCustomOffline> findByUser(String user_id);

    public OfCustomOffline findByMsgId(String msg_id);

    public List<OfCustomOffline> findAll();
}

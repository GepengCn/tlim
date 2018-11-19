package com.itonglian.dao;

import com.itonglian.entity.OfCustomOffline;
import com.itonglian.entity.OfMessage;

import java.util.List;

public interface OfflineDao {

    void add(OfCustomOffline ofCustomOffline);

    void updateStatus(String msg_id,int msg_status);

    void delete(String msg_id);

    void deleteByUser(String user_id);

    List<OfCustomOffline> findByUser(String user_id);

    OfCustomOffline findByMsgId(String msg_id);

    List<OfCustomOffline> findAll();
}

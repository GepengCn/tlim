package com.itonglian.dao;

import com.itonglian.bean.MessageRead;
import com.itonglian.entity.OfStatus;

import java.util.List;

public interface StatusDao {

    void add(OfStatus ofStatus);

    void update(String msg_id,String reader,int status);

    void delete(String msg_id);

    boolean isExist(String msg_id,String reader);

    void save(OfStatus ofStatus);

    List<MessageRead> findSessionRead(String session_id,int start,int length,String sender);

    List<MessageRead> findChatRead(String msg_from,String msg_to,int start,int length);

}

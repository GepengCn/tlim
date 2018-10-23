package com.itonglian.dao;

import com.itonglian.entity.OfMessage;

import java.util.List;

public interface MessageDao {

    public List<OfMessage> findHistory(String session_id,String user_id,int start, int length);


    public int findMessageTotal(String session_id,String user_id);

    public List<OfMessage> findChatHistory(String msg_from,String msg_to,int start, int length);

    public int findChatMessageTotal(String msg_from,String msg_to);

    public void deleteByUser(String session_id,String msg_from);

    public void deleteBySession(String session_id);

}

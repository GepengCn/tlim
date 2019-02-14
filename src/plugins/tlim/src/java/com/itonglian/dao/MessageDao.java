package com.itonglian.dao;

import com.itonglian.entity.Message;
import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;

import java.util.List;

public interface MessageDao {

    List<OfMessage> findHistory(String session_id,int start, int length);

    void insert(OfMessage ofMessage);

    int findMessageTotal(String session_id);

    List<OfMessage> findChatHistory(String msg_from,String msg_to,int start, int length);

    int findChatMessageTotal(String msg_from,String msg_to);

    void deleteByUser(String session_id,String msg_from);

    void deleteBySession(String session_id);

    String findMessageTime(String msg_id);

    List<Message> findMessageAfter(String msg_to, String msg_time);

    boolean deleteById(String msg_id);

    List<OfMessage> findSystemHistory(String msg_to,int start, int length);

    int findSystemMessageTotal(String msg_to);

    List<Message> findByTime(long msg_time);


}

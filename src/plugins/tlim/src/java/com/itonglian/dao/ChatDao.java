package com.itonglian.dao;

import com.itonglian.entity.OfChat;

import java.util.List;

public interface ChatDao {

     List<OfChat> chatList(String userId);

     boolean isExistChat(String msg_from,String msg_to);

     void add(OfChat ofChat);

     void modify(String chat_user,String chat_other);

     void clearChatHistory(String user_id,String other_id);

     void save(OfChat ofChat);


}

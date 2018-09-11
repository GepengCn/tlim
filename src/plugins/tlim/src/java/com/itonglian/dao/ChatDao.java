package com.itonglian.dao;

import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;

import java.util.List;
import java.util.Map;

public interface ChatDao {

    public void add(OfMessage ofMessage);

    public void delete(String msgId);

    public void update(OfMessage ofMessage);

    public OfMessage findEntityById(String msgId);

    public List<OfMessage> findList(Map<String,Object> conditions);

    public int isExist(String msgId,String msgTo);

    public List<OfChat> chatList(String userId);

    public boolean isExistChat(String msg_from,String msg_to);

    public void add(OfChat ofChat);

    public void modify(String chat_user,String chat_other);

    public void deleteOffline(String messageId);


}

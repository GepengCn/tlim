package com.itonglian.dao;

import com.itonglian.bean.SessionRead;
import com.itonglian.bean.SessionUnread;
import com.itonglian.entity.OfStatus;

import java.util.List;

public interface StatusDao {

    public void add(OfStatus ofStatus);

    public void update(String session_id,String msg_to);

    public List<OfStatus> query(String session_id,String msg_to);

    public void delete(String session_id,String msg_to);

    public List<SessionUnread> findUnread(String msg_to);

    public List<SessionRead> findMsgRead(String session_id);

    public List<SessionRead> findChatMsgRead(String session_id,String msg_to);

    public List<OfStatus> findMsgStatusList(String msg_id);

    public int readOrNot(String msg_id,String msg_to);

    public void updateByMsgId(String msg_id,String msg_to,int status);

    public boolean hasUnread(String user_id);




}

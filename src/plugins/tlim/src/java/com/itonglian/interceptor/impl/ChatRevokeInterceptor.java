package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.interceptor.ChatInterceptor;
import com.itonglian.utils.JsonUtils;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public class ChatRevokeInterceptor extends ChatInterceptor {

    private MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(true).setJgPush(true).setOffline(true);
        List<JsonUtils.Revoke> list = JsonUtils.getRevoke(protocol.getBody());
        Iterator<JsonUtils.Revoke> iterator = list.iterator();
        while(iterator.hasNext()){
            JsonUtils.Revoke revoke = iterator.next();
            messageDao.deleteById(revoke.getMsg_id());
        }

    }
}

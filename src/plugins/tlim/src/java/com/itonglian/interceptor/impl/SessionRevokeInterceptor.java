package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.interceptor.SessionInterceptor;
import com.itonglian.utils.JsonUtils;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public class SessionRevokeInterceptor extends SessionInterceptor {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    public void build(Protocol protocol, Message message) throws Exception {



        setOffline(true).setJgPush(true).setCanPersistent(true);

        List<JsonUtils.Revoke> revokeList = JsonUtils.getRevoke(protocol.getBody());
        Iterator<JsonUtils.Revoke> iterator1 = revokeList.iterator();
        while(iterator1.hasNext()){
            JsonUtils.Revoke revoke = iterator1.next();
            messageDao.deleteById(revoke.getMsg_id());
        }
    }
}

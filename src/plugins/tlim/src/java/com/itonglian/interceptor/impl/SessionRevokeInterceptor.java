package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.SessionInterceptor;
import com.itonglian.utils.JsonUtils;
import com.itonglian.utils.RevokeUtils;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public class SessionRevokeInterceptor extends SessionInterceptor {
    @Override
    public void build(Protocol protocol, Message message) throws Exception {

        setOffline(true).setJgPush(true).setCanPersistent(true);

        List<JsonUtils.Revoke> revokeList = JsonUtils.getRevoke(protocol.getBody());
        Iterator<JsonUtils.Revoke> iterator1 = revokeList.iterator();
        while(iterator1.hasNext()){
            JsonUtils.Revoke revoke = iterator1.next();
            RevokeUtils.handler(protocol.getMsg_to(),revoke.getMsg_id());
        }
    }
}

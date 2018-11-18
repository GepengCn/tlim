package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.SessionInterceptor;
import org.xmpp.packet.Message;

public class SessionCreatedInterceptor extends SessionInterceptor {
    @Override
    public void build(Protocol protocol, Message message) throws Exception {

        setOffline(true).setJgPush(true).setCanPersistent(true);
    }
}

package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.ChatInterceptor;
import org.xmpp.packet.Message;

public class ChatReadInterceptor extends ChatInterceptor {

    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(false).setJgPush(false).setOffline(true).setRead(true);
    }
}

package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.ChatInterceptor;
import org.xmpp.packet.Message;

public class ChatClearInterceptor extends ChatInterceptor {

    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(true).setJgPush(true).setOffline(true);
    }
}

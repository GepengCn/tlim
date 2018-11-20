package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.ChatInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

public class ChatReadInterceptor extends ChatInterceptor {

    private static final Logger Log = LoggerFactory.getLogger(ChatReadInterceptor.class);


    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(false).setJgPush(false).setOffline(false);
    }
}

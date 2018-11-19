package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

public class ChatReadInterceptor implements Interceptor {

    private static final Logger Log = LoggerFactory.getLogger(ChatReadInterceptor.class);


    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        Log.error("进入已读回执");
        Log.error("回执的消息="+protocol.getBody());

    }
}

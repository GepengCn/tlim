package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.ChatInterceptor;
import org.xmpp.packet.Message;

public class ChatSystemInterceptor extends ChatInterceptor {
    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        Log.error("ChatSystemInterceptor");
        setCanPersistent(false).setJgPush(false).setOffline(false).setCopyToMe(false);
    }
}

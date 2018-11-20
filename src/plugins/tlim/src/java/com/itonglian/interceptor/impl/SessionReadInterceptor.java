package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.SessionInterceptor;
import org.xmpp.packet.Message;

public class SessionReadInterceptor extends SessionInterceptor {

    @Override
    public void build(Protocol protocol, Message message) throws Exception {

        setOffline(false).setJgPush(false).setCanPersistent(false).setRead(true);

    }

}

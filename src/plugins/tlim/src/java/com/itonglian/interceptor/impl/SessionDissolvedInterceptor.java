package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.SessionInterceptor;
import com.itonglian.utils.DissolvedUtils;
import org.xmpp.packet.Message;

public class SessionDissolvedInterceptor extends SessionInterceptor {
    @Override
    public void build(Protocol protocol, Message message) throws Exception {

        setOffline(true).setJgPush(true).setCanPersistent(true);

        DissolvedUtils.handler(session_id);

    }
}

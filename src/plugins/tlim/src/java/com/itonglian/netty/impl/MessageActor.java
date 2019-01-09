package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.interceptor.InterceptorContext;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.utils.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class MessageActor implements NettyHttpActor {

    private static final Logger logger = LoggerFactory.getLogger(MessageActor.class);


    @Override
    public boolean execute(String jsonValue) {

        com.itonglian.entity.Message msg = JSONObject.parseObject(jsonValue,com.itonglian.entity.Message.class);
        InterceptorContext interceptorContext = new InterceptorContext();
        Message message = new Message();
        message.setFrom(new JID(MessageUtils.toJid(msg.getMsg_from())));
        message.setTo(new JID(MessageUtils.toJid(msg.getMsg_to())));
        message.setBody(jsonValue);
        message.setSubject("Remote");
        try {
            interceptorContext.handler(message);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return true;
    }
}

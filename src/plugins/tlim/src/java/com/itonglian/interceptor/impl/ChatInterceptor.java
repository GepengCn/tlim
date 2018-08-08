package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.interceptor.Interceptor;
import org.xmpp.packet.Message;

/**
 * <p> 概述：聊天类消息拦截器
 * <p> 功能：聊天类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class ChatInterceptor implements Interceptor {

    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsgId(protocol.getMsgId());

        ofMessage.setMsgType(protocol.getMsgType());

        ofMessage.setMsgFrom(protocol.getFrom());

        ofMessage.setMsgTo(protocol.getTo());

        ofMessage.setMsgTime(protocol.getMsgTime());

        ofMessage.setBody(protocol.getBody());

        chatDao.add(ofMessage);


    }


}

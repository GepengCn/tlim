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

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(protocol.getMsg_to());

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(protocol.getMsg_from());

        chatDao.add(ofMessage);


    }


}

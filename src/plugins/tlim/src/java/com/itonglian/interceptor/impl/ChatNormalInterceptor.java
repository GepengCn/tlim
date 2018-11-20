package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.User;
import com.itonglian.interceptor.ChatInterceptor;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.*;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * <p> 概述：聊天类消息拦截器
 * <p> 功能：聊天类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class ChatNormalInterceptor extends ChatInterceptor {

    private static final Logger Log = LoggerFactory.getLogger(ChatNormalInterceptor.class);


    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(true).setJgPush(true).setOffline(true);
    }




}

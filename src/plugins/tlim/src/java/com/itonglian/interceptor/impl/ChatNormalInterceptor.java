package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.ChatInterceptor;
import org.xmpp.packet.Message;

/**
 * <p> 概述：聊天类消息拦截器
 * <p> 功能：聊天类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class ChatNormalInterceptor extends ChatInterceptor {

    @Override
    public void build(Protocol protocol, Message message) throws Exception {
        setCanPersistent(true).setJgPush(true).setOffline(true);
    }




}

package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.SessionInterceptor;
import org.xmpp.packet.Message;

/**
 * <p> 概述：会话类消息拦截器
 * <p> 功能：会话类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class SessionNormalInterceptor extends SessionInterceptor {


    @Override
    public void build(Protocol protocol, Message message){

        setOffline(true).setJgPush(true).setCanPersistent(true);

    }
}

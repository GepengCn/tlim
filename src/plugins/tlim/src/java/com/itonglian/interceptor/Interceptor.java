package com.itonglian.interceptor;

import com.itonglian.bean.Protocol;
import com.itonglian.exception.ExceptionReply;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

/**
 * <p> 概述：消息拦截器
 * <p> 功能：消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public interface Interceptor {


    public abstract void handler(Protocol protocol, Message message) throws ExceptionReply;

}

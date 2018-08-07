package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.interceptor.Interceptor;
import org.xmpp.packet.Message;

/**
 * <p> 概述：命令类消息拦截器
 * <p> 功能：命令类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class CommandInterceptor implements Interceptor {
    @Override
    public void handler(Protocol protocol, Message message) {

    }
}

package com.itonglian;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.InterceptorContext;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Packet;

import java.io.File;

/**
 * <p> 概述：即时通讯接口插件
 * <p> 功能：即时通讯接口插件
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/1 15:16
 * <p> 类调用特殊情况：
 */
public class IMPlugin implements Plugin,PacketInterceptor {
    InterceptorManager interceptorManager = InterceptorManager.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(IMPlugin.class);


    UserDao userDao = UserDaoImpl.getInstance();
    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        interceptorManager.addInterceptor(this);
        userDao.clear();
        userDao.syncUser();
    }

    @Override
    public void destroyPlugin() {
        interceptorManager.removeInterceptor(this);
    }

    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {

        if((!incoming)||processed){
            return;
        }

        InterceptorContext interceptorContext = new InterceptorContext();

        try {
            interceptorContext.handler(packet,session);
        } catch (ExceptionReply exceptionReply) {
            Log.error(ExceptionUtils.getFullStackTrace(exceptionReply));
        }

    }
}

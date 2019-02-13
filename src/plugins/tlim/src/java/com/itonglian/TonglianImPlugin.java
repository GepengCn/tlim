package com.itonglian;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.interceptor.InterceptorContext;
import com.itonglian.netty.NettyClient;
import com.itonglian.netty.NettyServer;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.QuartzUtils;
import com.itonglian.utils.XMLProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

import java.io.File;

/**
 * <p> 概述：即时通讯接口插件
 * <p> 功能：即时通讯接口插件
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/1 15:16
 * <p> 类调用特殊情况：
 */
public class TonglianImPlugin implements Plugin,PacketInterceptor{
    InterceptorManager interceptorManager = InterceptorManager.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(TonglianImPlugin.class);


    UserDao userDao = UserDaoImpl.getInstance();

    private PluginManager pluginManager;



    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        this.pluginManager = manager;
        interceptorManager.addInterceptor(this);
        if(XMLProperties.getUserAsync()){
            userDao.clear();
            userDao.syncUser();
            QuartzUtils quartzUtils = new QuartzUtils();
            quartzUtils.run();
        }else{
            userDao.syncLocalUser();
        }
        XMLProperties.print();
        if(XMLProperties.getNettyServer()){
            new Thread(new NettyServer()).start();
        }
    }


    @Override
    public void destroyPlugin() {
        interceptorManager.removeInterceptor(this);
    }

    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {

        if(!incoming||processed){
            return;
        }

        InterceptorContext interceptorContext = new InterceptorContext();

        try {

            if(!(packet instanceof Message)){
                return;
            }

            if(!isValidPacket(packet)){
                return;
            }
            Message message = (Message)packet;

            interceptorContext.handler(message);

            if(XMLProperties.getNettyClient()){
                CustomThreadPool.getInstance().getFixedExecutorService().execute(new NettyClient("message", message.getBody()));
            }

        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
    private boolean isValidPacket(Packet packet){
        if(packet.getFrom() ==null || packet.getTo() ==null){
            return false;
        }
        return true;
    }

}

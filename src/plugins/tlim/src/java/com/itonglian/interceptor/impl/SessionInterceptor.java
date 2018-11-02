package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.bean.AsyncRunSaveDb;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 概述：会话类消息拦截器
 * <p> 功能：会话类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class SessionInterceptor implements Interceptor {

    static SessionDao sessionDao = SessionDaoImpl.getInstance();

    static SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    static ChatDao chatDao = ChatDaoImpl.getInstance();

    PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    private static final Logger Log = LoggerFactory.getLogger(SessionInterceptor.class);

    ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        List<Other> otherList = JSONArray.parseArray(protocol.getBody(),Other.class);

        Iterator<Other> otherIterator = otherList.iterator();

        while(otherIterator.hasNext()){

            Other other = otherIterator.next();

            ifOtherIsSession(other,protocol,message);
        }

    }


    private void ifOtherIsSession(Other other,Protocol protocol,Message message) throws Exception{
        String sessionId = other.getSessionId();

        if(isValidSession(sessionId,message)){

            batchRoute(sessionId,protocol,message);

        }

    }


    private boolean isValidSession(String sessionId,Message message) throws Exception {

        if(StringUtils.isNullOrEmpty(sessionId)){

            throw new ExceptionReply("error-007",message,packetDeliverer);

        }

        OfSession ofSession = sessionDao.findEntityById(sessionId);

        if(ofSession == null || StringUtils.isNullOrEmpty(ofSession.getSession_id())){

            throw new ExceptionReply("error-009",message,packetDeliverer);
        }

        return true;
    }

    private void batchRoute(String sessionId,Protocol protocol,Message message) throws Exception {

        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(sessionId);

        if(subscriberList == null){
            return;
        }

        Iterator<OfSubscriber> iterator = subscriberList.iterator();

        while(iterator.hasNext()){

            OfSubscriber ofSubscriber = iterator.next();

            String msgTo = ofSubscriber.getUser_id();

            executorService.execute(new AsyncRunSaveDb(protocol,sessionId,msgTo));

            /*if(protocol.getMsg_to().equals(msgTo)|| protocol.getMsg_from().equals(msgTo)){
                continue;
            }*/
            Message newMessage = message.createCopy();

            newMessage.setTo(new JID(MessageUtils.toJid(msgTo)));

            packetDeliverer.deliver(newMessage);


        }
    }


    private static class Other{
        private String sessionId;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }



}

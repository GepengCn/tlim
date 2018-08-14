package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
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
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

/**
 * <p> 概述：命令类消息拦截器
 * <p> 功能：命令类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class CommandInterceptor implements Interceptor {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    ChatDao chatDao = ChatDaoImpl.getInstance();


    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        String suffix = protocol.getMsgType().split("-")[1];

        String isSession = suffix.substring(0,1);

        if("1".equals(isSession)){
            List<Command> commandList = JSONObject.parseArray(protocol.getBody(),Command.class);
            Iterator<Command> textIterator = commandList.iterator();

            while(textIterator.hasNext()){

                Command command = textIterator.next();

                String sessionId = command.getSessionId();

                if(isValidSession(sessionId,message)){

                    batchRoute(sessionId,protocol,message);

                }

            }

        }


    }

    private boolean isValidSession(String sessionId,Message message) throws Exception {

        if(StringUtils.isNullOrEmpty(sessionId)){

            throw new ExceptionReply("error-007",message,packetDeliverer);

        }

        OfSession ofSession = sessionDao.findEntityById(sessionId);

        if(ofSession == null || StringUtils.isNullOrEmpty(ofSession.getSessionId())){

            throw new ExceptionReply("error-006",message,packetDeliverer);
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

            String msgTo = ofSubscriber.getUserId();

            if(StringUtils.isNullOrEmpty(msgTo)){
                continue;
            }

            if(protocol.getMsgTo().equals(msgTo)|| protocol.getMsgFrom().equals(msgTo)){
                continue;
            }

            message.setTo(MessageUtils.toJid(msgTo));

            packetDeliverer.deliver(message);
        }
    }
    private static class Command{

        private String msgId;

        private String sessionId;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}

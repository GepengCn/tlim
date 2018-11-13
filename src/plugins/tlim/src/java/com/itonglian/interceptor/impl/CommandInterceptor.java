package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.JID;
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

        String suffix = protocol.getMsg_type().split("-")[1];

        String isSession = suffix.substring(0,1);

        if("1".equals(isSession)){
            List<Command> commandList = JSONObject.parseArray(protocol.getBody(),Command.class);
            Iterator<Command> textIterator = commandList.iterator();

            while(textIterator.hasNext()){

                Command command = textIterator.next();

                String sessionId = command.getSession_id();

                if(isValidSession(sessionId,message)){

                    batchRoute(sessionId,protocol,message);

                }

            }

        }else{
            OfMessage ofMessage = new OfMessage();

            ofMessage.setMsg_id(protocol.getMsg_id());

            ofMessage.setMsg_type(protocol.getMsg_type());

            ofMessage.setMsg_from(protocol.getMsg_from());

            ofMessage.setMsg_to(protocol.getMsg_to());

            ofMessage.setMsg_time(protocol.getMsg_time());

            ofMessage.setBody(protocol.getBody());

            ofMessage.setSession_id(protocol.getMsg_to());

            chatDao.addThenSend(ofMessage);

        }


    }

    private boolean isValidSession(String sessionId,Message message) throws Exception {

        if(StringUtils.isNullOrEmpty(sessionId)){

            throw new ExceptionReply("error-007",message,packetDeliverer);

        }

        OfSession ofSession = sessionDao.findEntityById(sessionId);

        if(ofSession == null || StringUtils.isNullOrEmpty(ofSession.getSession_id())){

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

            String msgTo = ofSubscriber.getUser_id();

            if(StringUtils.isNullOrEmpty(msgTo)){
                continue;
            }

            OfMessage ofMessage = new OfMessage();

            ofMessage.setMsg_id(protocol.getMsg_id());

            ofMessage.setMsg_type(protocol.getMsg_type());

            ofMessage.setMsg_from(protocol.getMsg_from());

            ofMessage.setMsg_to(msgTo);

            ofMessage.setMsg_time(protocol.getMsg_time());

            ofMessage.setBody(protocol.getBody());

            ofMessage.setSession_id(sessionId);

            chatDao.addThenSend(ofMessage);

            sessionDao.modify(sessionId);

            if(protocol.getMsg_to().equals(msgTo)|| protocol.getMsg_from().equals(msgTo)){
                continue;
            }

            Message newMessage = message.createCopy();

            newMessage.setTo(new JID(MessageUtils.toJid(msgTo)));

            packetDeliverer.deliver(newMessage);
        }
    }
    private static class Command{

        private String msg_id;

        private String session_id;

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }
    }
}

package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.MessageRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.Message;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p> 概述：聊天类消息拦截器
 * <p> 功能：聊天类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class ChatInterceptor implements Interceptor {

    ChatDao chatDao = ChatDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    @Override
    public void handler(Protocol protocol,Message message) {

        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsgId(protocol.getMsgId());

        ofMessage.setMsgType(protocol.getMsgType());

        ofMessage.setMsgFrom(protocol.getFrom());

        ofMessage.setMsgTo(protocol.getTo());

        ofMessage.setTs(new Date().getTime()+"");

        ofMessage.setBody(protocol.getBody());

        chatDao.add(ofMessage);

        String suffix = protocol.getMsgType().split("-")[1];

        switch (suffix){
            case "000":

                Text text = JSONObject.parseObject(protocol.getBody(),Text.class);

                ifTextIsSession(text,protocol,message);

                break;
            case "001":
            case "002":
            case "003":

                File file = JSONObject.parseObject(protocol.getBody(),File.class);

                ifFileIsSession(file,protocol,message);

                break;
            default:
                break;
        }

    }

    private void ifTextIsSession(Text text,Protocol protocol,Message message){
        String sessionId = text.getSessionId();

        if(!StringUtils.isNullOrEmpty(sessionId)){
            //session_id不为空，代表是群消息
            batchRoute(sessionId,protocol,message);

        }
    }

    private void ifFileIsSession(File file,Protocol protocol,Message message){
        String sessionId = file.getSessionId();

        if(!StringUtils.isNullOrEmpty(sessionId)){
            //session_id不为空，代表是群消息
            batchRoute(sessionId,protocol,message);

        }
    }

    private void batchRoute(String sessionId,Protocol protocol,Message message){
        MessageRouter messageRouter = XMPPServer.getInstance().getMessageRouter();

        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(sessionId);

        Iterator<OfSubscriber> iterator = subscriberList.iterator();

        while(iterator.hasNext()){

            OfSubscriber ofSubscriber = iterator.next();

            String msgTo = ofSubscriber.getUserId();

            if(protocol.getFrom().equals(msgTo)){
                continue;
            }

            message.setTo(MessageUtils.toJid(msgTo));

            messageRouter.route(message);
        }
    }


    private class Text{
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

    private class File{
        private String fileId;

        private String sessionId;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}

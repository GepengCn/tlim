package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONArray;
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
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

/**
 * <p> 概述：会话类消息拦截器
 * <p> 功能：会话类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class SessionInterceptor implements Interceptor {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    ChatDao chatDao = ChatDaoImpl.getInstance();


    @Override
    public void handler(Protocol protocol, Message message) throws Exception {


        String suffix = protocol.getMsg_type().split("-")[1];

        switch (suffix){
            //处理文本消息类型
            case "000":

                List<Text> textList = JSONObject.parseArray(protocol.getBody(),Text.class);

                Iterator<Text> textIterator = textList.iterator();

                while(textIterator.hasNext()){

                    Text text = textIterator.next();

                    ifTextIsSession(text,protocol,message);
                }


                break;
            //图片、语音及文件类型
            case "001":
            case "002":
            case "003":

                List<File> fileList = JSONArray.parseArray(protocol.getBody(),File.class);

                Iterator<File> fileIterator = fileList.iterator();

                while(fileIterator.hasNext()){

                    File file = fileIterator.next();

                    ifFileIsSession(file,protocol,message);
                }


                break;
            default:
                break;
        }

    }


    private void ifTextIsSession(Text text,Protocol protocol,Message message) throws Exception {

        String sessionId = text.getSessionId();

        if(isValidSession(sessionId,message)){

            batchRoute(sessionId,protocol,message);

        }
    }

    private void ifFileIsSession(File file,Protocol protocol,Message message) throws Exception {

        String sessionId = file.getSessionId();

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

            chatDao.add(ofMessage);

            if(protocol.getMsg_to().equals(msgTo)|| protocol.getMsg_from().equals(msgTo)){
                continue;
            }

            message.setTo(MessageUtils.toJid(msgTo));

            packetDeliverer.deliver(message);
        }
    }


    private static class Text{

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

    private static class File{

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

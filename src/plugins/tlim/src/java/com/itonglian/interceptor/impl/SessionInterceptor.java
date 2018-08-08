package com.itonglian.interceptor.impl;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.entity.User;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.UserCacheManager;
import org.apache.commons.lang.StringUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.Message;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        JSONArray jsonArray = JSONArray.parseArray(protocol.getBody());
        if(jsonArray == null ||jsonArray.size()==0){
            throw new ExceptionReply("error-006",message,packetDeliverer);
        }

        SessionAddtion sessionAddtion = jsonArray.getObject(0,SessionAddtion.class);

        if(sessionAddtion ==null){
            throw new ExceptionReply("error-006",message,packetDeliverer);
        }

        // 保存订阅者
        List<OfSubscriber> list = sessionAddtion.getList();

        String sessionId = UUID.randomUUID().toString();

        ConcurrentHashMap<String,User> users = UserCacheManager.findAll();

        Iterator<OfSubscriber> iterator = list.iterator();

        List<String> sessionName = new ArrayList<String>();


        while(iterator.hasNext()){
            OfSubscriber ofSubscriber = iterator.next();
            User user = UserCacheManager.findUserByKey(ofSubscriber.getUserId());
            if(user==null){
                continue;
            }
            ofSubscriber.setSessionId(sessionId);
            ofSubscriber.setUserName(user.getUserName());
            ofSubscriber.setPic(user.getPicUrl());
            ofSubscriber.setAcctLogin(user.getAcctLogin());
            ofSubscriber.setTs(new Date().getTime()+"");
            subscriberDao.add(ofSubscriber);
            sessionName.add(user.getUserName());
        }

        // 保存会话

        OfSession ofSession = new OfSession();

        ofSession.setSessionId(sessionId);
        ofSession.setSessionName(StringUtils.join(sessionName,","));
        ofSession.setSessionType(sessionAddtion.getSessionType());
        ofSession.setSessionCreateTime(MessageUtils.getTs());
        ofSession.setSessionValid(0);
        ofSession.setSessionUser(protocol.getFrom());

        sessionDao.add(ofSession);
    }

    private class SessionAddtion{

        private int sessionType;

        private List<OfSubscriber> list;

        public int getSessionType() {
            return sessionType;
        }

        public void setSessionType(int sessionType) {
            this.sessionType = sessionType;
        }

        public List<OfSubscriber> getList() {
            return list;
        }

        public void setList(List<OfSubscriber> list) {
            this.list = list;
        }
    }



}

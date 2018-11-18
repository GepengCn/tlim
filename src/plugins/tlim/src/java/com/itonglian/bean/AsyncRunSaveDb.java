package com.itonglian.bean;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.CachePushFilter;
import com.itonglian.utils.DissolvedUtils;
import com.itonglian.utils.OfflineInterceptor;
import com.itonglian.utils.RevokeUtils;

import java.util.Iterator;
import java.util.List;

public class AsyncRunSaveDb implements Runnable{

    Protocol protocol;

    String sessionId;

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    ChatDao chatDao = ChatDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();


    public AsyncRunSaveDb(Protocol protocol,String sessionId) {
        this.protocol = protocol;
        this.sessionId = sessionId;
    }


    @Override
    public void run() {
        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(sessionId);

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(sessionId);

        chatDao.addNoRepeat(ofMessage);

        sessionDao.modify(sessionId);

        String msg_type = protocol.getMsg_type();

        /*if("MTS-101".equals(msg_type)){
            List<Revoke> revokeList = JSONArray.parseArray(protocol.getBody(),Revoke.class);
            Iterator<Revoke> iterator1 = revokeList.iterator();
            while(iterator1.hasNext()){
                Revoke revoke = iterator1.next();
                RevokeUtils.handler(protocol.getMsg_to(),revoke.getMsg_id());
            }

        }
        if("MTS-107".equals(msg_type)){
            DissolvedUtils.handler(sessionId);
        }*/
        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(sessionId);
        Iterator<OfSubscriber> iterator = subscriberList.iterator();
        while(iterator.hasNext()){
            OfSubscriber ofSubscriber = iterator.next();
            ofMessage.setMsg_to(ofSubscriber.getUser_id());
            CachePushFilter.getInstance().push(ofMessage);
            new OfflineInterceptor().handler(ofMessage);
        }
    }
    private static class Revoke{
        private String msg_id;

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }
    }
}

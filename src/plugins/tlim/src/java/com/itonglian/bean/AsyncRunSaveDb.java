package com.itonglian.bean;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.utils.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class AsyncRunSaveDb implements Runnable{

    Protocol protocol;

    String sessionId;

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    ChatDao chatDao = ChatDaoImpl.getInstance();

    String msgTo;


    public AsyncRunSaveDb(Protocol protocol,String sessionId,String msgTo) {
        this.protocol = protocol;
        this.sessionId = sessionId;
        this.msgTo = msgTo;
    }


    @Override
    public void run() {
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

        String msg_type = protocol.getMsg_type();

        if("MTS-101".equals(msg_type)){
            List<Revoke> revokeList = JSONArray.parseArray(protocol.getBody(),Revoke.class);
            Iterator<Revoke> iterator1 = revokeList.iterator();
            while(iterator1.hasNext()){
                Revoke revoke = iterator1.next();
                RevokeUtils.handler(protocol.getMsg_to(),revoke.getMsg_id());
            }

        }
        if("MTS-107".equals(msg_type)){
            DissolvedUtils.handler(sessionId);
        }
        CachePushFilter.getInstance().push(ofMessage);
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

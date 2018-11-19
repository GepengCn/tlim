package com.itonglian.interceptor;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.local.SessionPacketForward;
import com.itonglian.utils.CachePushFilter;
import com.itonglian.utils.JsonUtils;
import com.itonglian.utils.OfflineInterceptor;
import com.itonglian.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public abstract class SessionInterceptor implements Interceptor{

    private static final Logger Log = LoggerFactory.getLogger(SessionInterceptor.class);

    public abstract void build(Protocol protocol, Message message) throws Exception;

    protected String session_id;

    private SessionDao sessionDao = SessionDaoImpl.getInstance();

    private static MessageDao messageDao = MessageDaoImpl.getInstance();

    private SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    private boolean canOffline = false;

    private boolean canJgPush = false;

    private boolean canPersistent = false;

    private OfflineInterceptor offlineInterceptor = new OfflineInterceptor();

    public SessionInterceptor setOffline(boolean set){
        canOffline = set;
        return this;
    }

    public SessionInterceptor setJgPush(boolean set){
        canJgPush = set;
        return this;
    }

    public SessionInterceptor setCanPersistent(boolean set){
        canPersistent = set;
        return this;
    }

    public void handler(Protocol protocol, Message message) throws Exception{

        build(protocol,message);

        session_id = JsonUtils.getSessionId(protocol.getBody());

        if(StringUtils.isNullOrEmpty(session_id)){
            Log.error("[SessionReadInterceptor]session_id为空");
            return;
        }

        SessionPacketForward sessionPacketForward = new SessionPacketForward(message,session_id);

        sessionPacketForward.deliver();

        OfMessage ofMessage = protocolToMessage(protocol,session_id);

        persistent(ofMessage);
    }

    private OfMessage protocolToMessage(Protocol protocol,String session_id){
        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(session_id);

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(session_id);

        return ofMessage;
    }

    public void persistent(OfMessage ofMessage){

        if(canPersistent){
            messageDao.insert(ofMessage);
            sessionDao.modify(session_id);
        }
        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(session_id);
        Iterator<OfSubscriber> iterator = subscriberList.iterator();
        while(iterator.hasNext()){
            OfSubscriber ofSubscriber = iterator.next();
            ofMessage.setMsg_to(ofSubscriber.getUser_id());
            if(canOffline){
                offlineInterceptor.handler(ofMessage);
            }
            if(canJgPush){
                CachePushFilter.getInstance().push(ofMessage);
            }

        }
    }

}

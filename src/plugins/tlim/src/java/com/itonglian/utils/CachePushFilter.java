package com.itonglian.utils;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.cache.Cache;
import org.jivesoftware.util.cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Presence;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class CachePushFilter {


    private static final Logger Log = LoggerFactory.getLogger(CachePushFilter.class);

    private static class CachePushFilterHolder{
        private static CachePushFilter cachePushFilter=new CachePushFilter();
    }

    public static CachePushFilter getInstance(){
        return CachePushFilterHolder.cachePushFilter;
    }


    private Cache<String,String> cache ;

    private UserDao userDao = UserDaoImpl.getInstance();

    private SessionDao sessionDao = SessionDaoImpl.getInstance();

    PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();


    private CachePushFilter(){
        cache = CacheFactory.createCache("tlimCaches");
        cache.setMaxCacheSize(-1);
        cache.setMaxLifetime(3*1000*60);
    }

    public void push(OfMessage ofMessage){
        long nowTs = new Date().getTime();
        long msgTs = StringUtils.stringToLong(ofMessage.getMsg_time());
        long fiveMinutes = 3*60*1000;
        if(msgTs-nowTs>fiveMinutes){
            return;
        }
        String user_id = ofMessage.getMsg_to();

        if(ofMessage.getMsg_to().equals(ofMessage.getMsg_from())){
            return;
        }

        String appPushCode = userDao.findAppPushCodeByUserId(user_id);

        Collection<Presence> list = presenceManager.getPresences(ofMessage.getMsg_to());

        Iterator<Presence> iterator = list.iterator();

        boolean offline = true;

        boolean webOnline = false;

        boolean mobileOnline = false;

        while(iterator.hasNext()){
            Presence presence = iterator.next();
            if(presence!=null&&presence.getShow()==null){
                String resource = presence.getFrom().getResource();
                if(!StringUtils.isNullOrEmpty(resource)){
                    offline = false;
                }
                if(resource.contains("[web]")){
                    webOnline = true;
                }else{
                    mobileOnline = true;
                }
            }
        }
        if(!offline){
            if(mobileOnline){
                return;
            }
        }
        String combineKv = MessageUtils.combineKv(ofMessage.getMsg_id(),appPushCode);
        if(cache.containsKey(combineKv)){
            return;
        }
        cache.put(combineKv,"alive");
        String sessionName = "";
        if(ofMessage.getMsg_type().contains("MTS")){
            OfSession ofSession = sessionDao.findEntityById(ofMessage.getSession_id());
            if(ofSession!=null){
                sessionName = ofSession.getSession_name();
            }
        }
        String pushMsgStr = MessageUtils.messageContext(ofMessage,sessionName);
        if(!StringUtils.isNullOrEmpty(pushMsgStr)){
            Thread pushThread = new Thread(new JPushHandler(appPushCode,pushMsgStr,sessionName,combineKv));
            pushThread.setPriority(1);
            pushThread.start();
        }
    }

}

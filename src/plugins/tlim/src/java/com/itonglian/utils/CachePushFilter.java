package com.itonglian.utils;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import org.jivesoftware.util.cache.Cache;
import org.jivesoftware.util.cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

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
        String appPushCode = userDao.findAppPushCodeByUserId(user_id);
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
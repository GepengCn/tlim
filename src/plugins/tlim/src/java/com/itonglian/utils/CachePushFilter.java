package com.itonglian.utils;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.OfMessage;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.cache.Cache;
import org.jivesoftware.util.cache.CacheFactory;

import java.util.Date;

public class CachePushFilter {


    private static class CachePushFilterHolder{
        private static CachePushFilter cachePushFilter=new CachePushFilter();
    }

    public static CachePushFilter getInstance(){
        return CachePushFilterHolder.cachePushFilter;
    }


    private Cache<String,String> cache ;

    private UserDao userDao = UserDaoImpl.getInstance();

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
        try {
            String presence = UserPresenceManager.getPresence(user_id);
            if(!"offline".equals(presence)){
                return;
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(cache.containsKey(MessageUtils.combineKv(ofMessage.getMsg_id(),ofMessage.getMsg_to()))){
            return;
        }
        cache.put(MessageUtils.combineKv(ofMessage.getMsg_id(),ofMessage.getMsg_to()),"alive");
        String pushMsgStr = MessageUtils.messageContext(ofMessage);
        if(!StringUtils.isNullOrEmpty(pushMsgStr)){
            String appPushCode = userDao.findAppPushCodeByUserId(user_id);
            new Thread(new JPushHandler(appPushCode,MessageUtils.messageContext(ofMessage))).start();
        }
    }
}

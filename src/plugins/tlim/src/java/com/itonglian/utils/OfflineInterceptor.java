package com.itonglian.utils;

import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfCustomOffline;
import com.itonglian.entity.OfMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Presence;

import java.util.Collection;
import java.util.Iterator;

public class OfflineInterceptor {

    OfflineDao offlineDao = OfflineDaoImpl.getInstance();

    PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();

    private static final Logger Log = LoggerFactory.getLogger(OfflineInterceptor.class);


    public void handler(OfMessage ofMessage){


        Online msgToOnline =  queryOnline(ofMessage.getMsg_to());
        OfCustomOffline ofCustomOffline = MessageUtils.toOffline(ofMessage);
        if(!msgToOnline.isMobileOnline()){
            if(msgToOnline.isWebOnline()){
                ofCustomOffline.setMsg_status(1);
                offlineDao.add(ofCustomOffline);
            }else{
                offlineDao.add(ofCustomOffline);
            }
        }
        Online msgFromOnline = queryOnline(ofMessage.getMsg_from());
        if(msgFromOnline.isWebOnline()&&!msgFromOnline.isMobileOnline()){
            ofCustomOffline.setDelete_user(ofCustomOffline.getMsg_from());
            if(msgToOnline.isWebOnline()){
                ofCustomOffline.setMsg_status(1);
            }
            offlineDao.add(ofCustomOffline);
        }
    }
    private Online queryOnline(String user_id){

        //Log.error("queryOnline="+user_id);
        Collection<Presence> list = presenceManager.getPresences(user_id);

        Iterator<Presence> iterator = list.iterator();

        boolean online = false;

        boolean webOnline = false;

        boolean mobileOnline = false;

        while(iterator.hasNext()){
            Presence presence = iterator.next();
            if(presence!=null&&presence.getShow()==null){
                String resource = presence.getFrom().getResource();
                //Log.error("resource="+resource);
                if(!StringUtils.isNullOrEmpty(resource)){
                    online = true;
                }
                if(resource.contains("[web]")){
                    webOnline = true;
                }
                if(!resource.contains("[web]")){
                    mobileOnline = true;
                }
            }
        }
        return new Online(webOnline,mobileOnline,online);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Online{
        private boolean webOnline;

        private boolean mobileOnline;

        private boolean online;

    }

}

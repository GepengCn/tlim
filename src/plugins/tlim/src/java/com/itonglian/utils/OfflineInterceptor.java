package com.itonglian.utils;

import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfMessage;
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


        Collection<Presence> list = presenceManager.getPresences(ofMessage.getMsg_to());

        Log.error("OfflineInterceptor->handler");

        Log.error("ofMessage.getMsg_to()="+ofMessage.getMsg_to());

        Log.error("Collection<Presence>="+list.size());

        Iterator<Presence> iterator = list.iterator();

        boolean offline = true;

        boolean webOnline = false;

        while(iterator.hasNext()){
            Presence presence = iterator.next();
            Log.error("presence="+presence);
            if(presence!=null&&presence.getShow()==null){
                String resource = presence.getFrom().getResource();
                if(StringUtils.isNullOrEmpty(resource)||!resource.contains("[web]")){
                    offline = false;
                }

                if(resource.contains("[web]")){
                    webOnline = true;
                }
            }
        }
        Log.error("webOnline="+webOnline);
        Log.error("offline="+offline);
        if(offline){
            if(webOnline){
                Log.error("offlineDao.updateAfterAdd");
                offlineDao.updateAfterAdd(ofMessage,1);
            }else{
                Log.error("offlineDao.add");
                offlineDao.add(ofMessage);
            }
        }
    }

}

package com.itonglian.utils;

import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfCustomOffline;
import org.jivesoftware.util.cache.Cache;
import org.jivesoftware.util.cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OfflineAdapter {


    private static final Logger Log = LoggerFactory.getLogger(CachePushFilter.class);

    static OfflineDao offlineDao = OfflineDaoImpl.getInstance();

    private static class OfflineAdapterHolder{
        private static OfflineAdapter offlineAdapter=new OfflineAdapter();
    }

    public static OfflineAdapter getInstance(){
        return OfflineAdapterHolder.offlineAdapter;
    }

    private static Cache<Long,OfCustomOffline> cache = CacheFactory.createCache("Custom Offline Cache");;

    public OfflineAdapter() {
        init();
    }

    private static void init(){
        cache.setMaxCacheSize(-1);
        cache.setMaxLifetime(12*60*1000*60*7);
        List<OfCustomOffline> offlineList = offlineDao.findAll();

        Iterator<OfCustomOffline> iterator = offlineList.iterator();

        while(iterator.hasNext()){
            OfCustomOffline ofCustomOffline = iterator.next();
            long msg_id = ofCustomOffline.getId_();
            if(cache.containsKey(msg_id)){
                continue;
            }
            cache.put(msg_id,ofCustomOffline);
        }
    }

    private static List<OfCustomOffline> cacheToList(String key){
        List<OfCustomOffline> ofCustomOfflines = new ArrayList<>();

        Set<Long> set = cache.keySet();

        return null;
    }


    public List<OfCustomOffline> findByUser(String user_id){
        List<OfCustomOffline> ofCustomOfflines = new ArrayList<>();
        return null;
    }

}

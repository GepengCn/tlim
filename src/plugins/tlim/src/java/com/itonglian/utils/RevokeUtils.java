package com.itonglian.utils;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import org.jivesoftware.openfire.OfflineMessage;
import org.jivesoftware.openfire.OfflineMessageStore;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;

public class RevokeUtils {

    private static final Logger Log = LoggerFactory.getLogger(RevokeUtils.class);

    private static OfflineMessageStore offlineMessageStore =XMPPServer.getInstance().getOfflineMessageStore();

    private static ChatDao chatDao = ChatDaoImpl.getInstance();

    public static void handler(String user_id,String msg_id){

        chatDao.delete(msg_id);

        Collection<OfflineMessage> messageList = offlineMessageStore.getMessages(user_id,false);

        Iterator<OfflineMessage> iterator = messageList.iterator();
        while(iterator.hasNext()){
            OfflineMessage offlineMessage = iterator.next();
            Protocol protocol = JSONObject.parseObject(offlineMessage.getBody(),Protocol.class);
            if(protocol.getMsg_id().equals(msg_id)){
                chatDao.deleteOffline(offlineMessage.getMessageId());
            }
        }
    }

}

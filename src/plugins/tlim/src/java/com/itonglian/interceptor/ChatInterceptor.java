package com.itonglian.interceptor;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.User;
import com.itonglian.utils.*;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

import java.util.UUID;

public abstract class ChatInterceptor extends CommonInterceptor implements Interceptor{

    protected static final Logger Log = LoggerFactory.getLogger(ChatInterceptor.class);

    private ChatDao chatDao = ChatDaoImpl.getInstance();

    private PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    private MessageDao messageDao = MessageDaoImpl.getInstance();


    public abstract void build(Protocol protocol, Message message) throws Exception;

    private boolean canOffline = false;

    private boolean canJgPush = false;

    private boolean canPersistent = false;

    private boolean canThreadPool = true;

    private boolean canRead = false;

    private boolean canCopyToMe = true;

    private static final String ASYNC = "ASYNC";


    public ChatInterceptor setCopyToMe(boolean set){
        canCopyToMe = set;
        return this;
    }

    public ChatInterceptor setThreadPool(boolean set){
        canThreadPool = set;
        return this;
    }


    public ChatInterceptor setOffline(boolean set){
        canOffline = set;
        return this;
    }

    public ChatInterceptor setJgPush(boolean set){
        canJgPush = set;
        return this;
    }

    public ChatInterceptor setCanPersistent(boolean set){
        canPersistent = set;
        return this;
    }

    public ChatInterceptor setRead(boolean set){
        canRead = set;
        return this;
    }

    @Override
    public void handler(final Protocol protocol, Message message) throws Exception {

        if(ASYNC.equals(message.getSubject())){
            return;
        }

        build(protocol,message);

        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(protocol.getMsg_to());

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(protocol.getMsg_from());

        if(canPersistent){
            if(canThreadPool){
                final OfMessage ofMessage1= ofMessage;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        persistent(protocol,ofMessage1);
                    }
                });
                customThreadPool.getExecutorService().execute(thread);
            }else{
                persistent(protocol,ofMessage);
            }
        }
        if(canJgPush){
            CachePushFilter.getInstance().push(ofMessage);
        }
        if(canOffline){
            new OfflineInterceptor().handler(ofMessage);
        }

        if(canRead){
            handlerRead(protocol,message);
        }

        if(canCopyToMe){

            Message copyToMe = message.createCopy();

            copyToMe.setTo(message.getFrom().toBareJID());

            copyToMe.setSubject(ASYNC);

            packetDeliverer.deliver(copyToMe);
        }

        if("Remote".equals(message.getSubject())){
            packetDeliverer.deliver(message);
        }
    }

    private void persistent(Protocol protocol,OfMessage ofMessage){
        saveChat(protocol.getMsg_from(),protocol.getMsg_to());
        saveChat(protocol.getMsg_to(),protocol.getMsg_from());
        messageDao.insert(ofMessage);

    }

    private void saveChat(String msg_from,String msg_to){
        if(validate(msg_from,msg_to)){
            User fromUser = UserCacheManager.findUserByKey(msg_from);
            OfChat ofChat = new OfChat();
            String chatId =  UUID.randomUUID().toString();
            ofChat.setChat_id(chatId);
            ofChat.setChat_name(fromUser.getUser_name());
            ofChat.setChat_user(msg_from);
            ofChat.setChat_other(msg_to);
            ofChat.setChat_pic(fromUser.getPic_url());
            ofChat.setChat_create_time(MessageUtils.getTs());
            chatDao.save(ofChat);
        }
    }

    private boolean validate(String msg_from,String msg_to){
        if(StringUtils.isNullOrEmpty(msg_from)||StringUtils.isNullOrEmpty(msg_to)||msg_from.equals(msg_to)){
            return false;
        }
        return true;
    }
}

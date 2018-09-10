package com.itonglian.interceptor.impl;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.User;
import com.itonglian.interceptor.Interceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.UserCacheManager;
import org.xmpp.packet.Message;

import java.util.UUID;

/**
 * <p> 概述：聊天类消息拦截器
 * <p> 功能：聊天类消息拦截器
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class ChatInterceptor implements Interceptor {

    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    public void handler(Protocol protocol, Message message) throws Exception {

        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(protocol.getMsg_to());

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(protocol.getMsg_from());

        chatDao.add(ofMessage);

        if(!chatDao.isExistChat(protocol.getMsg_from(),protocol.getMsg_to())){
            addChat(protocol.getMsg_from(),protocol.getMsg_to());
        }else{
            chatDao.modify(protocol.getMsg_from(),protocol.getMsg_to());
        }
        if(!chatDao.isExistChat(protocol.getMsg_to(),protocol.getMsg_from())){
            addChat(protocol.getMsg_to(),protocol.getMsg_from());
        }else{
            chatDao.modify(protocol.getMsg_to(),protocol.getMsg_from());
        }

    }

    private void addChat(String msg_from,String msg_to){
        User fromUser = UserCacheManager.findUserByKey(msg_from);
        OfChat ofChat1 = new OfChat();
        String chatId1 =  UUID.randomUUID().toString();
        ofChat1.setChat_id(chatId1);
        ofChat1.setChat_name(fromUser.getUser_name());
        ofChat1.setChat_user(msg_from);
        ofChat1.setChat_other(msg_to);
        ofChat1.setChat_pic(fromUser.getPic_url());
        ofChat1.setChat_create_time(MessageUtils.getTs());
        chatDao.add(ofChat1);
    }
}

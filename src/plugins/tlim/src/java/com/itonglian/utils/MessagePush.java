package com.itonglian.utils;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.entity.OfMessage;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

import java.util.UUID;

public class MessagePush {

    private static final String ADMIN = "admin";

    private static PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    private static MessageDao messageDao = MessageDaoImpl.getInstance();

    public static final Logger Log = LoggerFactory.getLogger(MessagePush.class);


    public static void execute(String params,String msg_to,String msg_type) throws UnauthorizedException {
        Protocol protocol = new Protocol();

        protocol.setCompress("0");
        protocol.setEncode("1");
        protocol.setEncrypt("0");
        protocol.setVersion("2.0.0");
        protocol.setMsg_id(UUID.randomUUID().toString());
        protocol.setMsg_time(MessageUtils.getTs());
        protocol.setMsg_type(msg_type);
        protocol.setBody(params);
        protocol.setMsg_from(ADMIN);
        protocol.setMsg_to(msg_to);


        Message newMessage = new Message();

        newMessage.setType(Message.Type.chat);

        newMessage.setFrom(new JID(MessageUtils.toJid(ADMIN)));

        newMessage.setTo(new JID(MessageUtils.toJid(msg_to)));

        newMessage.setBody(JSONObject.toJSONString(protocol));

        packetDeliverer.deliver(newMessage);

        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(protocol.getMsg_to());

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(protocol.getMsg_from());

        messageDao.insert(ofMessage);

        new OfflineInterceptor().handler(ofMessage);

        CachePushFilter.getInstance().push(ofMessage);
    }
}

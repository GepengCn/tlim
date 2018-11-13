package com.itonglian.utils;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.entity.OfMessage;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

import java.util.UUID;

public class MessagePush {

    private static final String ADMIN = "admin";

    private static ChatDao chatDao = ChatDaoImpl.getInstance();

    private static PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();


    public static void execute(String params,String msg_to,String msg_type){
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

        try {
            packetDeliverer.deliver(newMessage);

        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
        OfMessage ofMessage = new OfMessage();

        ofMessage.setMsg_id(protocol.getMsg_id());

        ofMessage.setMsg_type(protocol.getMsg_type());

        ofMessage.setMsg_from(protocol.getMsg_from());

        ofMessage.setMsg_to(protocol.getMsg_to());

        ofMessage.setMsg_time(protocol.getMsg_time());

        ofMessage.setBody(protocol.getBody());

        ofMessage.setSession_id(protocol.getMsg_from());

        chatDao.addThenSend(ofMessage);
    }
}

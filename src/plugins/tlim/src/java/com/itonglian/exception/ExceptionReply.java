package com.itonglian.exception;

import org.jivesoftware.openfire.MessageRouter;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.PacketRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

public class ExceptionReply extends Exception{


    public ExceptionReply(String error,Packet packet,Session session) {
        Message message = (Message)packet;
        message.setTo(message.getFrom());
        message.setBody(error);
        session.process(message);
    }

    public ExceptionReply(String error,Message message,PacketDeliverer packetDeliverer) throws UnauthorizedException {
        message.setTo(message.getFrom());
        message.setBody(error);
        packetDeliverer.deliver(message);
    }
}

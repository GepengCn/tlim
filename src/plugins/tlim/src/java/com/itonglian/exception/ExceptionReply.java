package com.itonglian.exception;

import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;

public class ExceptionReply extends Exception{

/*
    public ExceptionReply(String error,Message message,Session session) throws PacketRejectedException {
        message.setTo(message.getFrom());
        message.setBody(error);
        session.process(message);
        PacketRejectedException rejectedException =  new PacketRejectedException();

        rejectedException.setRejectionMessage(error);

        throw rejectedException;
    }*/

    public ExceptionReply(String error,Message message,PacketDeliverer packetDeliverer) throws UnauthorizedException, PacketRejectedException {
        message.setTo(message.getFrom());

        message.setBody(error);

        packetDeliverer.deliver(message);

        PacketRejectedException rejectedException =  new PacketRejectedException();

        rejectedException.setRejectionMessage(error);

        throw rejectedException;
    }
}

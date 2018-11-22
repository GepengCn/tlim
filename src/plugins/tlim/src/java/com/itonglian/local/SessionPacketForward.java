package com.itonglian.local;

import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public class SessionPacketForward {

    private PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    private SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    private String session_id;

    private Message message;

    private static final String ASYNC = "ASYNC";


    public SessionPacketForward(Message message,String session_id) {
        this.session_id = session_id;
        this.message = message;
    }

    public void deliver(){


        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(session_id);

        Iterator<OfSubscriber> iterator = subscriberList.iterator();


        while(iterator.hasNext()){

            OfSubscriber ofSubscriber = iterator.next();

            String msgTo = ofSubscriber.getUser_id();

            Message newMessage = message.createCopy();

            newMessage.setTo(new JID(MessageUtils.toJid(msgTo)));


            try {
                packetDeliverer.deliver(newMessage);

            } catch (UnauthorizedException e) {
                e.printStackTrace();
            }



        }

    }


}

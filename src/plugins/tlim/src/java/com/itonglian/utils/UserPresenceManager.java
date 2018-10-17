package com.itonglian.utils;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

public class UserPresenceManager {

    private static XMPPServer server = XMPPServer.getInstance();

    private static PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();

    public static String getPresence(String user_id) throws UserNotFoundException {
        JID targetJID = new JID(MessageUtils.toJid(user_id));
        String status = "offline";
        User user = server.getUserManager().getUser(targetJID.getNode());
        Presence presence =presenceManager.getPresence(user);
        if (presence==null||presence.getShow() == null) {
            return"available";
        }else{
            return status;
        }

    }
}

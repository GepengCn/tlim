package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindPresence extends BaseServlet {

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    XMPPServer server = XMPPServer.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findPresence";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(session_id);

        Iterator<OfSubscriber> iterator = subscriberList.iterator();

        List<UserPres> userPresList = new ArrayList<UserPres>();

        PresenceManager presenceManager = XMPPServer.getInstance().getPresenceManager();

        while(iterator.hasNext()){
            OfSubscriber ofSubscriber = iterator.next();
            JID targetJID = new JID(MessageUtils.toJid(ofSubscriber.getUser_id()));
            User user =null;
            UserPres userPres = new UserPres(ofSubscriber.getUser_id(),"offline");
            try {

                user = server.getUserManager().getUser(targetJID.getNode());
                Presence presence =presenceManager.getPresence(user);
                if (presence == null) {
                }
                else if (presence.getShow() == null) {
                    userPres = new UserPres(ofSubscriber.getUser_id(),"available");
                }
                else if (presence.getShow().equals(org.xmpp.packet.Presence.Show.away)) {
                    userPres = new UserPres(ofSubscriber.getUser_id(),"away");
                }
                userPresList.add(userPres);
            } catch (UserNotFoundException e) {
                Log.error(e.getMessage());
            }
        }
        if(userPresList==null||userPresList.size()==0){
            return;
        }
        printWriter.append(JSONArray.toJSONString(userPresList));
        printWriter.flush();
        printWriter.close();
    }



    @Data
    @AllArgsConstructor
    private class UserPres{

        private String user_id;

        private String status;
    }
}

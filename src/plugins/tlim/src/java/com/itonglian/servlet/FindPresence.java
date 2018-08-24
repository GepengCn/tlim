package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindPresence extends HttpServlet {

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();


    XMPPServer server = XMPPServer.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(FindSessions.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findPresence");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MessageUtils.setResponse(resp);

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
                e.printStackTrace();
            }
        }
        if(userPresList==null||userPresList.size()==0){
            return;
        }
        printWriter.append(JSONArray.toJSONString(userPresList));
        printWriter.flush();
        printWriter.close();


    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private class UserPres{
        private String user_id;

        private String status;

        public UserPres(String user_id, String status) {
            this.user_id = user_id;
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

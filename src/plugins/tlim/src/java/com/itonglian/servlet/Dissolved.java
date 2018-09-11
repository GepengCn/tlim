package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

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
import java.util.UUID;

public class Dissolved extends HttpServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    MessageDao messageDao = MessageDaoImpl.getInstance();

    PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    ChatDao chatDao = ChatDaoImpl.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(Dissolved.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/dissolved");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        Protocol protocol = new Protocol();

        protocol.setCompress("0");
        protocol.setEncode("1");
        protocol.setEncrypt("0");
        protocol.setVersion("2.0.0");
        protocol.setMsg_id(UUID.randomUUID().toString());
        protocol.setMsg_time(MessageUtils.getTs());
        protocol.setMsg_type("MTS-107");
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body(session_id));
        protocol.setBody(JSONArray.toJSONString(bodies));

        try {
            batchRoute(session_id,protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sessionDao.delete(session_id);

        subscriberDao.deleteBySession(session_id);

        messageDao.deleteBySession(session_id);

        doBack(new BackJson("ok",""),printWriter);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void doBack(BackJson backJson, PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }

    private class BackJson{
        private String result;

        private String result_detail;


        public BackJson(String result, String result_detail) {
            this.result = result;
            this.result_detail = result_detail;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult_detail() {
            return result_detail;
        }

        public void setResult_detail(String result_detail) {
            this.result_detail = result_detail;
        }

    }

    private void batchRoute(String sessionId, Protocol protocol) throws Exception {

        List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(sessionId);

        if(subscriberList == null){
            return;
        }

        Iterator<OfSubscriber> iterator = subscriberList.iterator();

        while(iterator.hasNext()){

            OfSubscriber ofSubscriber = iterator.next();

            String msgTo = ofSubscriber.getUser_id();

            if(StringUtils.isNullOrEmpty(msgTo)){
                continue;
            }


            OfMessage ofMessage = new OfMessage();

            ofMessage.setMsg_id(protocol.getMsg_id());

            ofMessage.setMsg_type(protocol.getMsg_type());

            ofMessage.setMsg_from(msgTo);

            ofMessage.setMsg_to(msgTo);

            ofMessage.setMsg_time(protocol.getMsg_time());

            ofMessage.setBody(protocol.getBody());

            ofMessage.setSession_id(sessionId);

            chatDao.add(ofMessage);

            Message newMessage = new Message();

            newMessage.setType(Message.Type.chat);

            newMessage.setTo(new JID(MessageUtils.toJid(msgTo)));

            newMessage.setBody(JSONObject.toJSONString(protocol));

            packetDeliverer.deliver(newMessage);



        }
    }

    private static class Body{

        public Body(String session_id) {
            this.session_id = session_id;
        }

        private String session_id;

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }
    }
}

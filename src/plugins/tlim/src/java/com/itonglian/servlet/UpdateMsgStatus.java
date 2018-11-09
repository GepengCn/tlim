package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfStatus;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
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

public class UpdateMsgStatus extends HttpServlet {

    StatusDao statusDao = StatusDaoImpl.getInstance();

    private PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();

    private static final Logger Log = LoggerFactory.getLogger(UpdateMsgStatus.class);

    static SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/updateMsgStatus");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        String msg_to = req.getParameter("msg_to");


        List<OfStatus> ofStatusList = statusDao.query(session_id,msg_to);

        Iterator<OfStatus> iterator = ofStatusList.iterator();

        while(iterator.hasNext()){


            OfStatus ofStatus = iterator.next();

            if(ofStatus.getMsg_type().contains("MTT")){
                Protocol protocol = new Protocol();
                String msgId = UUID.randomUUID().toString();
                protocol.setCompress("0");
                protocol.setEncode("1");
                protocol.setEncrypt("0");
                protocol.setVersion("2.0.0");
                protocol.setMsg_id(msgId);
                protocol.setMsg_time(MessageUtils.getTs());
                String msg_type = "MTT-100";
                protocol.setMsg_type(msg_type);
                List<Body> bodies = new ArrayList<>();
                bodies.add(new Body(ofStatus.getMsg_id()));
                protocol.setBody(JSONArray.toJSONString(bodies));


                Message newMessage = new Message();

                newMessage.setType(Message.Type.chat);

                newMessage.setFrom(new JID(MessageUtils.toJid(msg_to)));

                newMessage.setTo(new JID(MessageUtils.toJid(session_id)));

                protocol.setMsg_from(msg_to);

                protocol.setMsg_to(session_id);

                newMessage.setBody(JSONObject.toJSONString(protocol));

                try {
                    packetDeliverer.deliver(newMessage);
                } catch (UnauthorizedException e) {
                    Log.error(ExceptionUtils.getFullStackTrace(e));
                }
            }else if(ofStatus.getMsg_type().contains("MTS")){
                Protocol protocol = new Protocol();

                String msgId = UUID.randomUUID().toString();
                protocol.setCompress("0");
                protocol.setEncode("1");
                protocol.setEncrypt("0");
                protocol.setVersion("2.0.0");
                protocol.setMsg_id(msgId);
                protocol.setMsg_time(MessageUtils.getTs());
                String msg_type = "MTS-100";
                protocol.setMsg_type(msg_type);

                List<Body> bodies = new ArrayList<>();
                bodies.add(new Body(ofStatus.getMsg_id(),ofStatus.getSession_id()));

                protocol.setBody(JSONArray.toJSONString(bodies));

                List<OfSubscriber> subscriberList = subscriberDao.findSubscribers(session_id);

                Iterator<OfSubscriber> subscriberIterator = subscriberList.iterator();

                while(subscriberIterator.hasNext()){
                    OfSubscriber ofSubscriber = subscriberIterator.next();
                    Message newMessage = new Message();

                    newMessage.setType(Message.Type.chat);

                    newMessage.setFrom(new JID(MessageUtils.toJid(msg_to)));

                    newMessage.setTo(new JID(MessageUtils.toJid(ofSubscriber.getUser_id())));

                    protocol.setMsg_from(msg_to);

                    protocol.setMsg_to(ofSubscriber.getUser_id());

                    newMessage.setBody(JSONObject.toJSONString(protocol));

                    try {
                        packetDeliverer.deliver(newMessage);
                    } catch (UnauthorizedException e) {
                        Log.error(ExceptionUtils.getFullStackTrace(e));
                    }
                }


            }





        }

        statusDao.update(session_id,msg_to);
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

    private static class Body{

        private String msg_id;

        private String session_id;

        public Body(String msg_id) {
            this.msg_id = msg_id;
        }

        public Body(String msg_id, String session_id) {
            this.msg_id = msg_id;
            this.session_id = session_id;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }
    }

}

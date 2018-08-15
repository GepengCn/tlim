package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.admin.AuthCheckFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindSubscribers extends HttpServlet {

    private static final Logger Log = LoggerFactory.getLogger(FindSession.class);

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findSubscribers");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String sessionId = req.getParameter("session_id");

        if(StringUtils.isNullOrEmpty(sessionId)){
            doBack(new BackJson("error-007","session_id为空",sessionId),printWriter);
            return;
        }

        OfSession ofSession = sessionDao.findEntityById(sessionId);

        if(ofSession == null){
            doBack(new BackJson("error-009","不存在的会话",sessionId),printWriter);
            return;
        }


        doBack(new BackJson("ok","",sessionId,ofSession.getSession_name(),ofSession.getSession_type(),ofSession.getSession_user(),subscriberDao.findSubscribers(sessionId)),printWriter);



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

        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        List<OfSubscriber> subscribers;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

        public BackJson(String result, String result_detail, String session_id, String session_name, int session_type, String session_user, List<OfSubscriber> subscribers) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
            this.session_name = session_name;
            this.session_type = session_type;
            this.session_user = session_user;
            this.subscribers = subscribers;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getSession_name() {
            return session_name;
        }

        public void setSession_name(String session_name) {
            this.session_name = session_name;
        }

        public int getSession_type() {
            return session_type;
        }

        public void setSession_type(int session_type) {
            this.session_type = session_type;
        }

        public String getSession_user() {
            return session_user;
        }

        public void setSession_user(String session_user) {
            this.session_user = session_user;
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

        public List<OfSubscriber> getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(List<OfSubscriber> subscribers) {
            this.subscribers = subscribers;
        }
    }


}

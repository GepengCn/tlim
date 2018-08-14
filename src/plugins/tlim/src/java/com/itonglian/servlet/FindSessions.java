package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.entity.OfSession;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindSessions extends HttpServlet {

    private static final Logger Log = LoggerFactory.getLogger(FindSessions.class);

    SessionDao sessionDao = SessionDaoImpl.getInstance();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findSessions");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        if(StringUtils.isNullOrEmpty(userId)){
            doBack(new BackJson("error-008","user_id为空",null),printWriter);
            return;
        }


        List<OfSession> ofSessions = sessionDao.findSessionsByUser(userId);

        doBack(new BackJson("ok","",translate(ofSessions)),printWriter);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private List<Session> translate(List<OfSession> src){
        if(src ==null || src.size()==0){
            return null;
        }

        Iterator<OfSession> iterator = src.iterator();
        List<Session> dest = new ArrayList<Session>();

        while(iterator.hasNext()){
            OfSession ofSession = iterator.next();
            Session session = new Session(ofSession.getSession_id(),
                    ofSession.getSession_name(),
                    ofSession.getSession_type(),
                    ofSession.getSession_user());
            dest.add(session);
        }
        return dest;

    }

    private void doBack(BackJson backJson, PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }

    private class BackJson{

        private String result;

        private String result_detail;

        private List<Session> sessions;


        public BackJson(String result, String result_detail, List<Session> sessions) {
            this.result = result;
            this.result_detail = result_detail;
            this.sessions = sessions;
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

        public List<Session> getSessions() {
            return sessions;
        }

        public void setSessions(List<Session> sessions) {
            this.sessions = sessions;
        }
    }


    private class Session{
        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        public Session(String session_id, String session_name, int session_type, String session_user) {
            this.session_id = session_id;
            this.session_name = session_name;
            this.session_type = session_type;
            this.session_user = session_user;
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
    }


}

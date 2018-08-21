package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.User;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import com.itonglian.utils.UserCacheManager;
import org.jivesoftware.admin.AuthCheckFilter;

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

public class FindWebSessions extends HttpServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();


    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findWebSessions");
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

        ofSessions.addAll(parseChat(userId));

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
            Session session = new Session(
                    ofSession.getSession_id(),
                    ofSession.getSession_name(),
                    ofSession.getSession_type(),
                    ofSession.getSession_user(),
                    ofSession.getSession_pic(),
                    ofSession.getSession_modify_time()
                    );
            dest.add(session);
        }

        return dest;

    }

    private List<OfSession> parseChat(String userId){
        List<OfSession> sessions = new ArrayList<OfSession>();

        List<OfMessage> messages = chatDao.tikTalk(userId);

        Iterator<OfMessage> iterator = messages.iterator();

        while(iterator.hasNext()){
            OfMessage ofMessage = iterator.next();
            OfSession ofSession = new OfSession();
            String msg_from = ofMessage.getMsg_from();
            String msg_to = ofMessage.getMsg_to();
            String session_id ="";
            if(msg_from.equals(userId)){
                //msg_to 是session_id
                session_id = msg_to;

            }else{
                //msg_from 是session_id
                session_id = msg_from;
            }
            ofSession.setSession_id(session_id);
            User user = UserCacheManager.findUserByKey(session_id);
            ofSession.setSession_name(user.getUser_name());
            ofSession.setSession_valid(0);
            ofSession.setSession_user(userId);
            ofSession.setSession_type(99);
            ofSession.setSession_pic(user.getPic_url());
            ofSession.setSession_modify_time(ofMessage.getMsg_time());
            sessions.add(ofSession);
        }
        return sessions;
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

        private String session_pic;

        private String session_modify_time;

        public Session(String session_id,
                       String session_name,
                       int session_type,
                       String session_user,
                       String session_pic,
                       String session_modify_time) {
            this.session_id = session_id;
            this.session_name = session_name;
            this.session_type = session_type;
            this.session_user = session_user;
            this.session_pic = session_pic;
            this.session_modify_time = session_modify_time;
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

        public String getSession_pic() {
            return session_pic;
        }

        public void setSession_pic(String session_pic) {
            this.session_pic = session_pic;
        }


        public String getSession_modify_time() {
            return session_modify_time;
        }

        public void setSession_modify_time(String session_modify_time) {
            this.session_modify_time = session_modify_time;
        }
    }
}

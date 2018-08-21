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

        doBack(new BackJson("ok","",ofSessions),printWriter);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
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

        private List<OfSession> sessions;


        public BackJson(String result, String result_detail, List<OfSession> sessions) {
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

        public List<OfSession> getSessions() {
            return sessions;
        }

        public void setSessions(List<OfSession> sessions) {
            this.sessions = sessions;
        }
    }

}

package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.OfChat;
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

public class FindWebSessions extends HttpServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(FindWebSessions.class);


    ChatDao chatDao = ChatDaoImpl.getInstance();

    UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findWebSessions");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");

        int valid = StringUtils.stringToInt(req.getParameter("valid"));

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();
        if(StringUtils.isNullOrEmpty(userId)){
            doBack(new BackJson("error-008","user_id为空",null),printWriter);
            return;
        }

        List<OfSession> ofSessions = sessionDao.findSessionsByUser(userId,valid);
        if(valid==0){
            ofSessions.addAll(parse(chatDao.chatList(userId)));
        }
        doBack(new BackJson("ok","",ofSessions),printWriter);

    }

    public List<OfSession> parse(List<OfChat> ofChats){
        Iterator<OfChat> iterator = ofChats.iterator();
        List<OfSession> ofSessions = new ArrayList<OfSession>();
        while(iterator.hasNext()){
            OfChat ofChat = iterator.next();
            OfSession ofSession = new OfSession();
            ofSession.setSession_id(ofChat.getChat_user());
            ofSession.setSession_name(ofChat.getChat_name());
            ofSession.setSession_valid(0);
            ofSession.setSession_type(99);
            ofSession.setSession_user(ofChat.getChat_other());
            ofSession.setSession_create_time(ofChat.getChat_create_time());
            ofSession.setSession_modify_time(ofChat.getChat_modify_time());
            ofSession.setSession_pic(ofChat.getChat_pic());
            ofSessions.add(ofSession);
        }
        return ofSessions;
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

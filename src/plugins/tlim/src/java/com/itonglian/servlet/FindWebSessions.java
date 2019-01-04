package com.itonglian.servlet;

import com.itonglian.dao.ChatDao;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.entity.OfChat;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindWebSessions extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    MessageDao messageDao = MessageDaoImpl.getInstance();


    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findWebSessions";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = req.getParameter("user_id");

        int valid = StringUtils.stringToInt(req.getParameter("valid"));

        PrintWriter printWriter = resp.getWriter();
        if(StringUtils.isNullOrEmpty(userId)){
            doBack(new BackJson("error-008","user_id为空",null),printWriter);
            return;
        }

        List<OfSession> ofSessions = sessionDao.findSessionsByUser(userId,valid);
        if(valid==0){
            ofSessions.addAll(parse(chatDao.chatList(userId)));
        }

        List<OfMessage> system = messageDao.findSystemHistory(userId,0,1);
        if(system.size()>0){
            ofSessions.add(parseSystem(system.get(0)));
        }
        doBack(new BackJson("ok","",ofSessions),printWriter);
    }

    private OfSession parseSystem(OfMessage ofMessage){
        OfSession ofSession = new OfSession();
        ofSession.setSession_id("systemMessage");
        ofSession.setSession_name("系统消息");
        ofSession.setSession_valid(0);
        ofSession.setSession_type(100);
        ofSession.setSession_user("admin");
        ofSession.setSession_modify_time(ofMessage.getMsg_time());
        return ofSession;
    }
    private List<OfSession> parse(List<OfChat> ofChats){
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

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;

        private List<OfSession> sessions;

    }

}

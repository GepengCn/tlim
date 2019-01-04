package com.itonglian.servlet;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
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

public class FindSessions extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findSessions";
    }


    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = req.getParameter("user_id");

        PrintWriter printWriter = resp.getWriter();

        if(StringUtils.isNullOrEmpty(userId)){
            doBack(new BackJson("error-008","user_id为空",null),printWriter);
            return;
        }

        List<OfSession> ofSessions = sessionDao.findSessionsByUser(userId,0);

        doBack(new BackJson("ok","",translate(ofSessions)),printWriter);
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
                    ofSession.getSession_user(),
                    ofSession.getSession_pic());
            dest.add(session);
        }
        return dest;

    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;

        private List<Session> sessions;

    }


    @Data
    @AllArgsConstructor
    private class Session{
        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        private String session_pic;

    }


}

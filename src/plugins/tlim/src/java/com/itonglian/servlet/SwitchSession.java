package com.itonglian.servlet;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SwitchSession extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/switchSession";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String session_id = req.getParameter("session_id");

        int valid = StringUtils.stringToInt(req.getParameter("session_valid"));

        submit(session_id,valid);
    }

    public boolean submit(String session_id,int valid){
        return sessionDao.switchSession(session_id,valid);
    }
}

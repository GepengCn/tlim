package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.entity.OfSession;
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

        resp.setCharacterEncoding("utf-8");

        resp.setContentType("application/json;charset=utf-8");

        PrintWriter printWriter = resp.getWriter();

        if(StringUtils.isNullOrEmpty(userId)){
            doBack(new BackJson("error-008","user_id为空",null),printWriter);
            return;
        }


        List<OfSession> ofSessions = sessionDao.findSessionsByUser(userId);


        doBack(new BackJson("ok","",ofSessions),printWriter);

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

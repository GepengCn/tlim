package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
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

public class UpdateSessionPic extends HttpServlet {

    private static final Logger Log = LoggerFactory.getLogger(FindSession.class);

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/updateSessionPic");

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

        String sessionPic = req.getParameter("session_pic");

        sessionDao.updatePic(sessionId,sessionPic);

        doBack(new BackJson("ok","",sessionId,sessionPic),printWriter);

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

        private String session_pic;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

        public BackJson(String result, String result_detail, String session_id, String session_pic) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
            this.session_pic = session_pic;
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

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getSession_pic() {
            return session_pic;
        }

        public void setSession_pic(String session_pic) {
            this.session_pic = session_pic;
        }
    }

}

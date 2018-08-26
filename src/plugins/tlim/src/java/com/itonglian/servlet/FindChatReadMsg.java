package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.SessionRead;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.utils.MessageUtils;
import org.jivesoftware.admin.AuthCheckFilter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindChatReadMsg extends HttpServlet {

    StatusDao statusDao = StatusDaoImpl.getInstance();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findChatReadMsg");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        String msg_to = req.getParameter("msg_to");

        List<SessionRead> sessionReads = statusDao.findChatMsgRead(session_id,msg_to);

        sessionReads.addAll(statusDao.findChatMsgRead(msg_to,session_id));

        doBack(new BackJson("ok","",sessionReads),printWriter);


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

        List<SessionRead> sessionReads;

        public BackJson(String result, String result_detail, List<SessionRead> sessionReads) {
            this.result = result;
            this.result_detail = result_detail;
            this.sessionReads = sessionReads;
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

        public List<SessionRead> getSessionReads() {
            return sessionReads;
        }

        public void setSessionReads(List<SessionRead> sessionReads) {
            this.sessionReads = sessionReads;
        }
    }
}

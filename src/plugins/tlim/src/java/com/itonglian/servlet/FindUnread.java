package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.SessionUnread;
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

public class FindUnread extends HttpServlet {


    StatusDao statusDao = StatusDaoImpl.getInstance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findUnread");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String msg_to = req.getParameter("msg_to");

        doBack(new BackJson("ok","",statusDao.findUnread(msg_to)),printWriter);

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

        List<SessionUnread> sessionUnreadList;

        public BackJson(String result, String result_detail, List<SessionUnread> sessionUnreadList) {
            this.result = result;
            this.result_detail = result_detail;
            this.sessionUnreadList = sessionUnreadList;
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

        public List<SessionUnread> getSessionUnreadList() {
            return sessionUnreadList;
        }

        public void setSessionUnreadList(List<SessionUnread> sessionUnreadList) {
            this.sessionUnreadList = sessionUnreadList;
        }
    }

}

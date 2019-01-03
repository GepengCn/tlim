package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.MessageDaoOracleImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.admin.AuthCheckFilter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindSessionHistory extends HttpServlet {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    MessageDao messageDaoOracle = MessageDaoOracleImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findSessionHistory");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        int start = StringUtils.stringToInt(req.getParameter("start"));

        int length =StringUtils.StringToMaxInt(req.getParameter("length"));

        List<OfMessage> messageList = messageDao.findHistory(session_id,start,length);

        int total = messageDao.findMessageTotal(session_id);

        doBack(new BackJson("ok","",session_id,messageList,total),printWriter);


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

        private List<OfMessage> message_list;

        private int total;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

        public BackJson(String result, String result_detail, String session_id, List<OfMessage> message_list,int total) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
            this.message_list = message_list;
            this.total = total;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
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

        public List<OfMessage> getMessage_list() {
            return message_list;
        }

        public void setMessage_list(List<OfMessage> message_list) {
            this.message_list = message_list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}

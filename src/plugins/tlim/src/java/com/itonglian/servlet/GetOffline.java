package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfCustomOffline;
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

public class GetOffline extends HttpServlet {

    OfflineDao offlineDao = OfflineDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/getOffline");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String user_id = req.getParameter("user_id");

        String getThenClear = req.getParameter("getThenClear");

        boolean clear = true;

        if(StringUtils.isNullOrEmpty(getThenClear)||!"1".equals(getThenClear)){
            clear = false;
        }
        doBack(new BackJson("ok","",offlineDao.findByUser(user_id)),printWriter);

        if(clear){
            offlineDao.deleteByUser(user_id);
        }

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

        List<OfCustomOffline> message_list;


        public BackJson(String result, String result_detail) {
            this.result = result;
            this.result_detail = result_detail;
        }

        public BackJson(String result, String result_detail, List<OfCustomOffline> message_list) {
            this.result = result;
            this.result_detail = result_detail;
            this.message_list = message_list;
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

        public List<OfCustomOffline> getMessage_list() {
            return message_list;
        }

        public void setMessage_list(List<OfCustomOffline> message_list) {
            this.message_list = message_list;
        }
    }
}

package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.PubactDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.PubactDaoImpl;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.entity.OfStatus;
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

public class FindStatusList extends HttpServlet {

    StatusDao statusDao = StatusDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findStatusList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String msg_id = req.getParameter("msg_id");

        String sender = req.getParameter("sender");

        List<OfStatus> ofStatusList = statusDao.findByMsgId(msg_id,sender);

        doBack(new BackJson("ok","",ofStatusList),printWriter);

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

        private List<OfStatus> ofStatusList;

        public BackJson(String result, String result_detail, List<OfStatus> ofStatusList) {
            this.result = result;
            this.result_detail = result_detail;
            this.ofStatusList = ofStatusList;
        }

        public List<OfStatus> getOfStatusList() {
            return ofStatusList;
        }

        public void setOfStatusList(List<OfStatus> ofStatusList) {
            this.ofStatusList = ofStatusList;
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

    }
}

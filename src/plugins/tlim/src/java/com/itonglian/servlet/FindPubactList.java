package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.PubactDao;
import com.itonglian.dao.impl.PubactDaoImpl;
import com.itonglian.entity.OfPubact;
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

public class FindPubactList extends HttpServlet {

    PubactDao pubactDao = PubactDaoImpl.getInstance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findPubactList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        List<OfPubact> pubactList = pubactDao.findBySession(session_id);

        doBack(new BackJson("ok","",pubactList),printWriter);

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

        List<OfPubact> pubactList;


        public BackJson(String result, String result_detail, List<OfPubact> pubactList) {
            this.result = result;
            this.result_detail = result_detail;
            this.pubactList = pubactList;
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

        public List<OfPubact> getPubactList() {
            return pubactList;
        }

        public void setPubactList(List<OfPubact> pubactList) {
            this.pubactList = pubactList;
        }
    }
}

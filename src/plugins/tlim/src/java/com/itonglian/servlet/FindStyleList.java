package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.StyleDao;
import com.itonglian.dao.impl.StyleDaoImpl;
import com.itonglian.entity.OfStyle;
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

public class FindStyleList extends HttpServlet {

    StyleDao styleDao = StyleDaoImpl.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/findStyleList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String user_id = req.getParameter("user_id");

        doBack(new BackJson("ok","",styleDao.query(user_id)),printWriter);

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

        private List<OfStyle> ofStyleList;

        public BackJson(String result, String result_detail, List<OfStyle> ofStyleList) {
            this.result = result;
            this.result_detail = result_detail;
            this.ofStyleList = ofStyleList;
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

        public List<OfStyle> getOfStyleList() {
            return ofStyleList;
        }

        public void setOfStyleList(List<OfStyle> ofStyleList) {
            this.ofStyleList = ofStyleList;
        }
    }
}

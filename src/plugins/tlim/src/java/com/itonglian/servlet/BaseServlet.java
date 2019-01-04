package com.itonglian.servlet;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

public abstract class BaseServlet extends HttpServlet {

    public static final Logger Log = LoggerFactory.getLogger(BaseServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude(mapper());
    }

    protected abstract String mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageUtils.setResponse(resp);
        execute(req,resp);
        doBack(new BackJson("ok",""),resp.getWriter());
    }

    protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    protected void doBack(BackJson backJson, PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected class BackJson{

        private String result;
        private String result_detail;

    }
}

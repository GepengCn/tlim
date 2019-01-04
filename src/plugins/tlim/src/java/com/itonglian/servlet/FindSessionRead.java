package com.itonglian.servlet;

import com.itonglian.bean.MessageRead;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindSessionRead extends BaseServlet {

    StatusDao statusDao = StatusDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findSessionRead";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        String sender = req.getParameter("sender");

        int start = StringUtils.stringToInt(req.getParameter("start"));

        int length =StringUtils.StringToMaxInt(req.getParameter("length"));

        doBack(new BackJson("ok","",statusDao.findSessionRead(session_id,start,length,sender)),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        private List<MessageRead> messageReadList;

    }
}

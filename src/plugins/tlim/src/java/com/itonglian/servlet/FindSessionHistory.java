package com.itonglian.servlet;

import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.entity.OfMessage;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindSessionHistory extends BaseServlet {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findSessionHistory";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        int start = StringUtils.stringToInt(req.getParameter("start"));

        int length =StringUtils.StringToMaxInt(req.getParameter("length"));

        List<OfMessage> messageList = messageDao.findHistory(session_id,start,length);

        int total = messageDao.findMessageTotal(session_id);

        doBack(new BackJson("ok","",session_id,messageList,total),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;

        private String session_id;

        private List<OfMessage> message_list;

        private int total;

    }
}

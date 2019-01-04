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

public class FindSystemHistory extends BaseServlet {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findSystemHistory";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String msg_to = req.getParameter("msg_to");

        int start = StringUtils.stringToInt(req.getParameter("start"));

        int length =StringUtils.StringToMaxInt(req.getParameter("length"));

        List<OfMessage> messageList = messageDao.findSystemHistory(msg_to,start,length);

        int total = messageDao.findSystemMessageTotal(msg_to);

        doBack(new BackJson("ok","",messageList,total),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;


        private List<OfMessage> message_list;

        private int total;

    }
}

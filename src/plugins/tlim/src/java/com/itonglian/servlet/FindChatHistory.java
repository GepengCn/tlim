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

public class FindChatHistory extends BaseServlet {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findChatHistory";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        String user_id = req.getParameter("user_id");

        int start = StringUtils.stringToInt(req.getParameter("start"));

        int length =StringUtils.StringToMaxInt(req.getParameter("length"));

        if(StringUtils.isNullOrEmpty(user_id)){
            doBack(new BackJson("error-008","user_id为空",session_id),printWriter);
            return;
        }
        List<OfMessage>  messageList = messageDao.findChatHistory(session_id,user_id,start,length);

        int total = messageDao.findChatMessageTotal(session_id,user_id);

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

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

    }
}

package com.itonglian.servlet;

import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.entity.Message;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SyncMessage extends BaseServlet {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/syncMessage";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String msg_to = req.getParameter("msg_to");

        String msg_id = req.getParameter("msg_id");

        String msg_time = messageDao.findMessageTime(msg_id);

        if(StringUtils.isNullOrEmpty(msg_time)){
            doBack(new BackJson("ok",""),printWriter);
        }

        doBack(new BackJson("ok","",messageDao.findMessageAfter(msg_to,msg_time)),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        private List<Message> messageList;

        public BackJson(String result, String result_detail) {
            this.result = result;
            this.result_detail = result_detail;
        }

    }
}

package com.itonglian.servlet;

import com.itonglian.servlet.common.MessagePushExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApprovalBack extends BaseServlet {

    private MessagePushExecutor messagePushExecutor = new MessagePushExecutor();

    @Override
    protected String mapper() {
        return "tlim/approvalBack";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String params = req.getParameter("params");

        String msg_to = req.getParameter("msgTo");

        String msg_type = "MTB-001";

        submit(params,msg_to,msg_type);
    }

    public boolean submit(String params, String msg_to, String msg_type) {
        return messagePushExecutor.submit(params,msg_to,msg_type);
    }

}

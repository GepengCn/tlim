package com.itonglian.servlet;

import com.itonglian.utils.MessagePush;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApprovalBack extends BaseServlet {

    @Override
    protected String mapper() {
        return "tlim/approvalBack";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String params = req.getParameter("params");

        String msg_to = req.getParameter("msgTo");

        String msg_type = "MTB-001";

        MessagePush.execute(params,msg_to,msg_type);
    }

}

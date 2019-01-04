package com.itonglian.servlet;

import com.itonglian.utils.MessagePush;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Approval extends BaseServlet{
    @Override
    protected String mapper() {
        return "tlim/approval";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String params = req.getParameter("params");

        String msg_to = req.getParameter("msgTo");

        String msg_type = "MTB-000";

        MessagePush.execute(params,msg_to,msg_type);
    }

}

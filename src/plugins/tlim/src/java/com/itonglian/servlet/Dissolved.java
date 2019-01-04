package com.itonglian.servlet;

import com.itonglian.utils.DissolvedUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dissolved extends BaseServlet {

    @Override
    protected String mapper() {
        return "tlim/dissolved";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String session_id = req.getParameter("session_id");

        DissolvedUtils.handler(session_id);
    }

}

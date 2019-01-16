package com.itonglian.servlet;

import com.itonglian.utils.DissolvedUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

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

        submit(session_id);
    }

    public boolean submit(String session_id){
        try {
            DissolvedUtils.handler(session_id);
        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return true;
    }

}

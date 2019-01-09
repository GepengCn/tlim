package com.itonglian.servlet;

import com.itonglian.dao.PubactDao;
import com.itonglian.dao.impl.PubactDaoImpl;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPubact extends BaseServlet {

    PubactDao pubactDao = PubactDaoImpl.getInstance();


    @Override
    protected String mapper() {
        return "tlim/addPubact";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String title = req.getParameter("title");

        String content = req.getParameter("content");

        String user_id = req.getParameter("user_id");

        String session_id = req.getParameter("session_id");

        submit(title,content,user_id,session_id);

    }

    public boolean submit(String title,String content,String user_id,String session_id){
        try {
            return pubactDao.add(title,content,user_id,session_id);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
    }

}

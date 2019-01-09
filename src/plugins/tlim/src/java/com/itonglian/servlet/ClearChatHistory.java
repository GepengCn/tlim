package com.itonglian.servlet;

import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearChatHistory extends BaseServlet {

    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/clearChatHistory";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user_id = req.getParameter("user_id");

        String other_id = req.getParameter("other_id");

        submit(user_id,other_id);
    }

    public boolean submit(String user_id,String other_id){
        boolean success;
        try {
            success = chatDao.clearChatHistory(user_id,other_id);
            success = chatDao.clearChatHistory(other_id,user_id);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return success;
    }

}

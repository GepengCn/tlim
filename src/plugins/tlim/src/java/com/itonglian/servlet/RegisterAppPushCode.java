package com.itonglian.servlet;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterAppPushCode extends BaseServlet {

    UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/registerAppPushCode";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user_id = req.getParameter("user_id");

        String registrationId = req.getParameter("registrationId");

        submit(user_id,registrationId);
    }

    public boolean submit(String user_id,String registrationId){
        try {
            userDao.registerAppPushCode(user_id,registrationId);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return true;
    }

}

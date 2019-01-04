package com.itonglian.servlet;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;

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

        userDao.registerAppPushCode(user_id,registrationId);
    }

}

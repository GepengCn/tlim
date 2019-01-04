package com.itonglian.servlet;

import com.itonglian.dao.MessageDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import org.jivesoftware.admin.AuthCheckFilter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
public class RevokeMessage extends BaseServlet {


    MessageDao messageDao = MessageDaoImpl.getInstance();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/revokeMessage");
    }

    @Override
    protected String mapper() {
        return "tlim/revokeMessage";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String msg_id = req.getParameter("msg_id");

        messageDao.deleteById(msg_id);
    }

}

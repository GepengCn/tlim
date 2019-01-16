package com.itonglian.servlet;

import com.itonglian.dao.PubactDao;
import com.itonglian.dao.impl.PubactDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModifyPubact extends BaseServlet {
    PubactDao pubactDao = PubactDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/modifyPubact";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id_ = req.getParameter("id_");

        String title = req.getParameter("title");

        String content = req.getParameter("content");

        submit(id_,title,content);
    }

    public boolean submit(String id_,String title,String content){
        return pubactDao.update(id_,title,content);
    }
}

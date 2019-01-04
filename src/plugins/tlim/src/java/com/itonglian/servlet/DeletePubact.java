package com.itonglian.servlet;

import com.itonglian.dao.PubactDao;
import com.itonglian.dao.impl.PubactDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePubact extends BaseServlet {

    PubactDao pubactDao = PubactDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/deletePubact";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id_ = req.getParameter("id_");

        pubactDao.delete(id_);
    }
}

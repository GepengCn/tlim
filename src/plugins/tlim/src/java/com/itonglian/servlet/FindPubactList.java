package com.itonglian.servlet;

import com.itonglian.dao.PubactDao;
import com.itonglian.dao.impl.PubactDaoImpl;
import com.itonglian.entity.OfPubact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindPubactList extends BaseServlet {

    PubactDao pubactDao = PubactDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findPubactList";
    }


    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String session_id = req.getParameter("session_id");

        List<OfPubact> pubactList = pubactDao.findBySession(session_id);

        doBack(new BackJson("ok","",pubactList),printWriter);
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        List<OfPubact> pubactList;

    }
}

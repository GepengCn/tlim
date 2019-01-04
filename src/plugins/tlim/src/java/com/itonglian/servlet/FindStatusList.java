package com.itonglian.servlet;

import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.entity.OfStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindStatusList extends BaseServlet {

    StatusDao statusDao = StatusDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findStatusList";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String msg_id = req.getParameter("msg_id");

        String sender = req.getParameter("sender");

        List<OfStatus> ofStatusList = statusDao.findByMsgId(msg_id,sender);

        doBack(new BackJson("ok","",ofStatusList),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        private List<OfStatus> ofStatusList;

    }
}

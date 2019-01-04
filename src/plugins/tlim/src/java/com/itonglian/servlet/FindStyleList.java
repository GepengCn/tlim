package com.itonglian.servlet;

import com.itonglian.dao.StyleDao;
import com.itonglian.dao.impl.StyleDaoImpl;
import com.itonglian.entity.OfStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindStyleList extends BaseServlet {

    StyleDao styleDao = StyleDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findStyleList";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String user_id = req.getParameter("user_id");

        doBack(new BackJson("ok","",styleDao.query(user_id)),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        private List<OfStyle> ofStyleList;

    }
}

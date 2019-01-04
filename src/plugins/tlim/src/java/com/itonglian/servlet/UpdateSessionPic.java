package com.itonglian.servlet;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateSessionPic extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/updateSessionPic";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String sessionId = req.getParameter("session_id");

        if(StringUtils.isNullOrEmpty(sessionId)){
            doBack(new BackJson("error-007","session_id为空",sessionId),printWriter);
            return;
        }

        String sessionPic = req.getParameter("session_pic");

        sessionDao.updatePic(sessionId,sessionPic);

        doBack(new BackJson("ok","",sessionId,sessionPic),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;

        private String session_id;

        private String session_pic;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

    }

}

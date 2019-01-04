package com.itonglian.servlet;

import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FindSubscribers extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/findSubscribers";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String sessionId = req.getParameter("session_id");

        if(StringUtils.isNullOrEmpty(sessionId)){
            doBack(new BackJson("error-007","session_id为空",sessionId),printWriter);
            return;
        }

        OfSession ofSession = sessionDao.findEntityById(sessionId);

        if(ofSession == null){
            doBack(new BackJson("error-009","不存在的会话",sessionId),printWriter);
            return;
        }

        doBack(new BackJson("ok","",sessionId,ofSession.getSession_name(),ofSession.getSession_type(),ofSession.getSession_user(),subscriberDao.findSubscribers(sessionId)),printWriter);
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {

        private String result;

        private String result_detail;

        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        List<OfSubscriber> subscribers;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

    }


}

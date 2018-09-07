package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.entity.User;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import com.itonglian.utils.UserCacheManager;
import org.jivesoftware.admin.AuthCheckFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class ModifySession extends HttpServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    MessageDao messageDao = MessageDaoImpl.getInstance();

    StatusDao statusDao = StatusDaoImpl.getInstance();

    private static final Logger Log = LoggerFactory.getLogger(ModifySession.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/modifySession");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String sessionId = req.getParameter("session_id");

        String sessionName = req.getParameter("session_name");

        String subscribers = req.getParameter("subscribers");

        if(StringUtils.isNullOrEmpty(sessionId)){
            doBack(new BackJson("error-007","session_id为空",sessionId),printWriter);
            return;
        }


        //更新session
        if(!StringUtils.isNullOrEmpty(sessionName)){
            sessionDao.updateNameById(sessionId,sessionName);
        }

        //更新订阅者
        handlerSubscribers(subscribers,sessionId);

        doBack(new BackJson("ok","",sessionId,MessageUtils.getTs()),printWriter);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void doBack(BackJson backJson, PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }


    private void handlerSubscribers(String subscribers,String sessionId){

        List<HandlerSubcriber> subcriberList = JSONArray.parseArray(subscribers,HandlerSubcriber.class);

        if(subcriberList==null){
            return;
        }

        Iterator<HandlerSubcriber> iterator = subcriberList.iterator();

        while(iterator.hasNext()){

            HandlerSubcriber handlerSubcriber = iterator.next();

            String type = handlerSubcriber.getType();

            if(StringUtils.isNullOrEmpty(type)){
                continue;
            }

            switch (type){
                case "add":
                    User user = UserCacheManager.findUserByKey(handlerSubcriber.getUserId());
                    if(user == null){
                        break;
                    }
                    subscriberDao.delete(handlerSubcriber.getUserId(),sessionId);
                    OfSubscriber ofSubscriber = new OfSubscriber();
                    ofSubscriber.setUser_id(user.getUser_id());
                    ofSubscriber.setUser_name(user.getUser_name());
                    ofSubscriber.setAcct_login(user.getAcct_login());
                    ofSubscriber.setPic(user.getPic_url());
                    ofSubscriber.setTs(MessageUtils.getTs());
                    ofSubscriber.setSession_id(sessionId);
                    subscriberDao.add(ofSubscriber);
                    break;
                case "del":
                    subscriberDao.delete(handlerSubcriber.getUserId(),sessionId);
                    messageDao.deleteByUser(sessionId,handlerSubcriber.getUserId());
                    statusDao.delete(sessionId,handlerSubcriber.getUserId());
                    break;
                default:
                    break;


            }

        }


    }

    private class BackJson{

        private String result;

        private String result_detail;

        private String session_id;

        private String session_modify_time;

        public BackJson(String result, String result_detail, String session_id) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
        }

        public BackJson(String result, String result_detail, String session_id, String session_modify_time) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
            this.session_modify_time = session_modify_time;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult_detail() {
            return result_detail;
        }

        public void setResult_detail(String result_detail) {
            this.result_detail = result_detail;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getSession_modify_time() {
            return session_modify_time;
        }

        public void setSession_modify_time(String session_modify_time) {
            this.session_modify_time = session_modify_time;
        }
    }

    private static class HandlerSubcriber{

        private String userId;

        private String type;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

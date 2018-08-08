package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.UserOnlyId;
import com.itonglian.dao.SessionDao;
import com.itonglian.dao.SubscriberDao;
import com.itonglian.dao.impl.SessionDaoImpl;
import com.itonglian.dao.impl.SubscriberDaoImpl;
import com.itonglian.entity.OfSession;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.entity.User;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.UserCacheManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AddSession extends HttpServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();


    private static final Logger Log = LoggerFactory.getLogger(AddSession.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/addSession");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionType = req.getParameter("session_type");

        String requestUser = req.getParameter("request_user");

        String subscribers = req.getParameter("subscribers");

        int intSessionType =com.itonglian.utils.StringUtils.stringToInt(sessionType);

        PrintWriter printWriter = resp.getWriter();

        try {

            if(com.itonglian.utils.StringUtils.isNullOrEmpty(sessionType)){
                doBack(new BackJson("error-002","session_type为空或无效",intSessionType),printWriter);
                return;
            }

            if(com.itonglian.utils.StringUtils.isNullOrEmpty(requestUser)){
                doBack(new BackJson("error-004","session_user为空或不存在",intSessionType),printWriter);
                return;
            }



            List<OfSubscriber> list = JSONArray.parseArray(subscribers,OfSubscriber.class);


            String sessionId = UUID.randomUUID().toString();

            ConcurrentHashMap<String,User> users = UserCacheManager.findAll();

            Iterator<OfSubscriber> iterator = list.iterator();

            List<String> sessionNameList = new ArrayList<String>();

            List<UserOnlyId> userOnlyIds = new ArrayList<UserOnlyId>();

            int i=0;
            while(iterator.hasNext()){
                OfSubscriber ofSubscriber = iterator.next();
                User user = UserCacheManager.findUserByKey(ofSubscriber.getUserId());
                if(user==null){
                    continue;
                }
                ofSubscriber.setSessionId(sessionId);
                ofSubscriber.setUserName(user.getUserName());
                ofSubscriber.setPic(user.getPicUrl());
                ofSubscriber.setAcctLogin(user.getAcctLogin());
                ofSubscriber.setTs(MessageUtils.getTs());
                subscriberDao.add(ofSubscriber);
                sessionNameList.add(user.getUserName());
                userOnlyIds.add(new UserOnlyId(user.getUserId()));
            }

            // 保存会话

            OfSession ofSession = new OfSession();

            ofSession.setSessionId(sessionId);

            String sessionName = StringUtils.join(sessionNameList,",");

            String sessionCreateTime = MessageUtils.getTs();

            ofSession.setSessionName(StringUtils.join(sessionNameList,","));
            ofSession.setSessionType(intSessionType);
            ofSession.setSessionCreateTime(sessionCreateTime);
            ofSession.setSessionValid(0);
            ofSession.setSessionUser(requestUser);

            sessionDao.add(ofSession);

            BackJson backJson = new BackJson(
                    "ok",
                    "",
                    sessionId,
                    sessionName,
                    intSessionType,
                    requestUser,
                    sessionCreateTime,
                    userOnlyIds
            );
            doBack(backJson,printWriter);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            doBack(new BackJson("error-002",e.getMessage(),intSessionType),printWriter);
        }



    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }


    private void doBack(BackJson backJson,PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }




    private class BackJson{

        private String result;

        private String result_detail;

        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        private String session_create_time;

        private List<UserOnlyId> subscribers;

        public BackJson(String result, String result_detail, String session_id, String session_name, int session_type, String session_user, String session_create_time, List<UserOnlyId> subscribers) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_id = session_id;
            this.session_name = session_name;
            this.session_type = session_type;
            this.session_user = session_user;
            this.session_create_time = session_create_time;
            this.subscribers = subscribers;
        }

        public BackJson(String result, String result_detail, int session_type) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_type = session_type;
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

        public String getSession_name() {
            return session_name;
        }

        public void setSession_name(String session_name) {
            this.session_name = session_name;
        }

        public int getSession_type() {
            return session_type;
        }

        public void setSession_type(int session_type) {
            this.session_type = session_type;
        }

        public String getSession_user() {
            return session_user;
        }

        public void setSession_user(String session_user) {
            this.session_user = session_user;
        }

        public String getSession_create_time() {
            return session_create_time;
        }

        public void setSession_create_time(String session_create_time) {
            this.session_create_time = session_create_time;
        }

        public List<UserOnlyId> getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(List<UserOnlyId> subscribers) {
            this.subscribers = subscribers;
        }
    }




}

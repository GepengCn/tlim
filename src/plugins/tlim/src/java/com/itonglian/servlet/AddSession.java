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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AddSession extends BaseServlet {

    SessionDao sessionDao = SessionDaoImpl.getInstance();

    SubscriberDao subscriberDao = SubscriberDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/addSession";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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

            while(iterator.hasNext()){
                OfSubscriber ofSubscriber = iterator.next();
                User user = UserCacheManager.findUserByKey(ofSubscriber.getUser_id());
                if(user==null){
                    continue;
                }
                ofSubscriber.setUser_id(ofSubscriber.getUser_id());
                ofSubscriber.setSession_id(sessionId);
                ofSubscriber.setUser_name(user.getUser_name());
                ofSubscriber.setPic(user.getPic_url());
                ofSubscriber.setAcct_login(user.getAcct_login());
                ofSubscriber.setTs(MessageUtils.getTs());
                subscriberDao.add(ofSubscriber);
                sessionNameList.add(user.getUser_name());
                userOnlyIds.add(new UserOnlyId(user.getUser_id()));
            }

            // 保存会话

            OfSession ofSession = new OfSession();

            ofSession.setSession_id(sessionId);

            String sessionName = StringUtils.join(sessionNameList,",");

            if(sessionName.length()>=200){
                sessionName = sessionName.substring(0,200);
            }

            String sessionCreateTime = MessageUtils.getTs();

            ofSession.setSession_name(sessionName);
            ofSession.setSession_type(intSessionType);
            ofSession.setSession_create_time(sessionCreateTime);
            ofSession.setSession_valid(0);
            ofSession.setSession_user(requestUser);
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
            doBack(new BackJson("error-010",e.getMessage(),intSessionType),printWriter);
        }
    }

    @Override
    protected void doBack(BaseServlet.BackJson backJson, PrintWriter printWriter) {
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }


    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor()
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson{

        private String result;

        private String result_detail;

        private String session_id;

        private String session_name;

        private int session_type;

        private String session_user;

        private String session_create_time;

        private List<UserOnlyId> subscribers;


        public BackJson(String result, String result_detail, int session_type) {
            this.result = result;
            this.result_detail = result_detail;
            this.session_type = session_type;
        }

    }




}

package com.itonglian.servlet;

import com.alibaba.fastjson.JSON;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.netty.NettyClient;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.XMLProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterAppPushCode extends BaseServlet {

    UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/registerAppPushCode";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user_id = req.getParameter("user_id");

        String registrationId = req.getParameter("registrationId");

        boolean success = submit(user_id,registrationId);

        if(XMLProperties.getNettyClient()&&success){
            CustomThreadPool.getInstance().getExecutorService().execute(new NettyClient(MessageUtils.getMapper(mapper()), JSON.toJSONString(new Param(user_id,registrationId))));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Param{
        private String user_id;
        private String registrationId;
    }
    public boolean submit(String user_id,String registrationId){
        try {
            userDao.registerAppPushCode(user_id,registrationId);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return true;
    }

}

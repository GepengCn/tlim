package com.itonglian.servlet;

import com.alibaba.fastjson.JSON;
import com.itonglian.dao.ChatDao;
import com.itonglian.dao.impl.ChatDaoImpl;
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

public class ClearChatHistory extends BaseServlet {

    ChatDao chatDao = ChatDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/clearChatHistory";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user_id = req.getParameter("user_id");

        String other_id = req.getParameter("other_id");

        boolean success = submit(user_id,other_id);

        if(XMLProperties.getNettyClient()&&success){
            CustomThreadPool.getInstance().getExecutorService().execute(new NettyClient(MessageUtils.getMapper(mapper()), JSON.toJSONString(new Param(user_id,other_id))));
        }
    }

    public boolean submit(String user_id,String other_id){
        boolean success;
        try {
            success = chatDao.clearChatHistory(user_id,other_id);
            if(!success){
                return false;
            }
            success = chatDao.clearChatHistory(other_id,user_id);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return success;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Param{
        private String user_id;
        private String other_id;

    }

}

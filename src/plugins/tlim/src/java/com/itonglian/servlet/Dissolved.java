package com.itonglian.servlet;

import com.alibaba.fastjson.JSON;
import com.itonglian.netty.NettyClient;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.DissolvedUtils;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.XMLProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dissolved extends BaseServlet {

    @Override
    protected String mapper() {
        return "tlim/dissolved";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String session_id = req.getParameter("session_id");

        boolean success = submit(session_id);

        if(XMLProperties.getNettyClient()&&success){
            CustomThreadPool.getInstance().getExecutorService().execute(new NettyClient(MessageUtils.getMapper(mapper()), JSON.toJSONString(new Param(session_id))));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Param{
        private String session_id;
    }

    public boolean submit(String session_id){
        try {
            DissolvedUtils.handler(session_id);
        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return true;
    }

}

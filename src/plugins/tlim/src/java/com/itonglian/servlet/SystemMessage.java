package com.itonglian.servlet;

import com.alibaba.fastjson.JSON;
import com.itonglian.netty.NettyClient;
import com.itonglian.servlet.common.MessagePushExecutor;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.XMLProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SystemMessage extends BaseServlet {

    private MessagePushExecutor messagePushExecutor = new MessagePushExecutor();

    @Override
    protected String mapper() {
        return "tlim/systemMessage";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String params = req.getParameter("params");

        String msg_to = req.getParameter("msgTo");

        String msg_type = "MTB-100";

        boolean success = submit(params,msg_to,msg_type);

        if(XMLProperties.getNettyClient()&&success){
            CustomThreadPool.getInstance().getExecutorService().execute(new NettyClient("systemMessage",JSON.toJSONString(new systemMessageJson(params,msg_to,msg_type))));
        }
    }


    public boolean submit(String params, String msg_to, String msg_type) {
        return messagePushExecutor.submit(params,msg_to,msg_type);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class systemMessageJson{

        private String params;

        private String msg_to;

        private String msg_type;
    }


}

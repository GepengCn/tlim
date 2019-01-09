package com.itonglian.servlet.common;

import com.itonglian.servlet.BaseServlet;
import com.itonglian.utils.MessagePush;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagePushExecutor {

    public static final Logger Log = LoggerFactory.getLogger(BaseServlet.class);

    public boolean submit(String params,String msg_to,String msg_type){

        try {
            MessagePush.execute(params,msg_to,msg_type);
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return true;
    }
}

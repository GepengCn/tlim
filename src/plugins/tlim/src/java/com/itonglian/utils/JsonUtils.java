package com.itonglian.utils;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils {

    private static final Logger Log = LoggerFactory.getLogger(JsonUtils.class);

    public static String getSessionId(String jsonStr){

        Log.error("jsonStr"+jsonStr);

        List<SessionEntity> sessionEntities = JSONArray.parseArray(jsonStr,SessionEntity.class);

        Log.error("sessionEntities.size()="+sessionEntities.size());

        if(sessionEntities==null||sessionEntities.size()==0){
            return null;
        }

        SessionEntity sessionEntity = sessionEntities.get(0);

        Log.error("SessionEntity="+sessionEntity);

        if(sessionEntity==null){
            return null;
        }

        Log.error("getSession_id="+sessionEntity.getSession_id());
        return sessionEntity.getSession_id();
    }

    public static List<Revoke> getRevoke(String jsonStr){
        List<Revoke> revokeList = JSONArray.parseArray(jsonStr,Revoke.class);
        return revokeList;
    }


    public static class SessionEntity{
        private String session_id;

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }
    }

    public static class Revoke{

        private String msg_id;

        private String msg_to;

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getMsg_to() {
            return msg_to;
        }

        public void setMsg_to(String msg_to) {
            this.msg_to = msg_to;
        }
    }
}
